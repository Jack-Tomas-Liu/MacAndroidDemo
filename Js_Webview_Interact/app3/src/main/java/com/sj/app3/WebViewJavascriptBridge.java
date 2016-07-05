package com.sj.app3;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wecook.common.R;
import com.wecook.common.core.debug.Logger;
import com.wecook.common.utils.JsonUtils;
import com.wecook.common.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * webview的js和native桥接
 */
public class WebViewJavascriptBridge {

    private static final String BRIDGE_NAME = "WecookJSBridge";
    private static final String TAG = "jsbridge";

    private WebView mWebView;
    private Activity mContext;
    private WVJBHandler mSimpleMessageHandlers;
    private Map<String, WVJBHandler> messageHandlers;
    private Map<String, WVJBResponseCallback> responseCallbacks;
    private long uniqueId;

    public WebViewJavascriptBridge(Activity context, WebView webview) {
        mContext = context;
        mWebView = webview;
        messageHandlers = new HashMap<String, WVJBHandler>();
        responseCallbacks = new HashMap<String, WVJBResponseCallback>();
        uniqueId = 0;
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(this, "_" + BRIDGE_NAME);
    }

    public void setSimpleJBHandler(WVJBHandler handler) {
        mSimpleMessageHandlers = handler;
    }

    public void loadWebViewJavascriptBridgeJs(WebView webView) {
        InputStream is = mContext.getResources().openRawResource(R.raw.wecookjsbridge);
        String script = convertStreamToString(is);
        if (!TextUtils.isEmpty(script)) {
            webView.loadUrl("javascript:" + script);
        }
    }

    public static String convertStreamToString(InputStream is) {
        String s = "";
        try {
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) s = scanner.next();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public interface WVJBHandler {
        public void handle(String data, WVJBResponseCallback jsCallback);
    }

    public interface WVJBResponseCallback {
        public void callback(String data);
    }

    public void registerHandler(String handlerName, WVJBHandler handler) {
        messageHandlers.put(handlerName, handler);
    }

    private class CallbackJs implements WVJBResponseCallback {
        private final String callbackIdJs;

        public CallbackJs(String callbackIdJs) {
            this.callbackIdJs = callbackIdJs;
        }

        @Override
        public void callback(String data) {
            try {
                callbackJs(callbackIdJs, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void callbackJs(String callbackIdJs, String data) throws JSONException {
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("responseId", callbackIdJs);
        if (JsonUtils.isJsonString(data)) {
            message.put("responseData", data);
        } else if (JsonUtils.isJsonObject(data)) {
            message.put("responseData", JsonUtils.getJSONObject(data));
        } else if (JsonUtils.isJsonArray(data)) {
            message.put("responseData", JsonUtils.getJSONArray(data));
        }

        dispatchMessage(message);
    }

    @JavascriptInterface
    public void _handleMessageFromJs(String data, String responseId,
                                     String responseData, String callbackId, String handlerName) {

        if (null != responseId) {
            WVJBResponseCallback responseCallback = responseCallbacks.get(responseId);
            responseCallback.callback(responseData);
            responseCallbacks.remove(responseId);
        } else {
            WVJBResponseCallback responseCallback = null;
            if (null != callbackId) {
                responseCallback = new CallbackJs(callbackId);
            }
            WVJBHandler handler;
            if (null != handlerName) {
                handler = messageHandlers.get(handlerName);
                if (null == handler) {
                    return;
                }
            } else {
                handler = mSimpleMessageHandlers;
            }
            try {
                handler.handle(data, responseCallback);
            } catch (Exception exception) {
            }
        }
    }

    public void send(String data) {
        send(data, null);
    }

    public void send(String data, WVJBResponseCallback responseCallback) {
        try {
            sendData(data, responseCallback, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendData(String data, WVJBResponseCallback responseCallback, String handlerName) throws JSONException {
        Map<String, Object> message = new HashMap<String, Object>();
        if (JsonUtils.isJsonString(data)) {
            message.put("data", data);
        } else if (JsonUtils.isJsonObject(data)) {
            message.put("data", JsonUtils.getJSONObject(data));
        } else if (JsonUtils.isJsonArray(data)) {
            message.put("data", JsonUtils.getJSONArray(data));
        }
        if (null != responseCallback) {
            String callbackId = "java_cb_" + (++uniqueId);
            responseCallbacks.put(callbackId, responseCallback);
            message.put("callbackId", callbackId);
        }
        if (null != handlerName) {
            message.put("handlerName", handlerName);
        }
        dispatchMessage(message);
    }

    private void dispatchMessage(Map<String, Object> message) {
        String messageJSON = new JSONObject(message).toString();
        final String javascriptCommand =
                String.format("javascript:" + BRIDGE_NAME + "._handleMessageFromNative('%s');", messageJSON);
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWebView != null && null != javascriptCommand) {
                    mWebView.loadUrl(javascriptCommand);
                }
            }
        });
    }


    public void callHandler(String handlerName) {
        callHandler(handlerName, null, null);
    }

    public void callHandler(String handlerName, String data) {
        callHandler(handlerName, data, null);
    }

    public void callHandler(String handlerName, String data, WVJBResponseCallback responseCallback) {
        try {
            sendData(data, responseCallback, handlerName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
