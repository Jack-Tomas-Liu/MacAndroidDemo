;(function() {
	if (window.WecookJSBridge) { return }

	var messageHandlers = {};
	var responseCallbacks = {};
	var uniqueId = 1;

	function init(messageHandler) {
		if (WecookJSBridge._messageHandler) { throw new Error('WecookJSBridge.init called twice') }
		WecookJSBridge._messageHandler = messageHandler;
	}

	function send(data, responseCallback) {
		_doSend({ data:data }, responseCallback);
	}

	function registerHandler(handlerName, handler) {
		messageHandlers[handlerName] = handler;
	}

	function callHandler(handlerName, data, responseCallback) {
	    var jsonData = JSON.stringify(data);
		_doSend({ handlerName:handlerName, data:jsonData }, responseCallback);
	}

	function _handleMessageFromNative(messageJSON) {
		_dispatchMessageFromNative(messageJSON);
	}

	function _doSend(message, responseCallback) {
        console.log("js responseCallback:" + responseCallback);
        if (responseCallback) {
            var callbackId = 'cb_'+(uniqueId++)+'_' + new Date().getTime();
            responseCallbacks[callbackId] = responseCallback;
            message['callbackId'] = callbackId;
        }
        console.log("js sending:"+JSON.stringify(message));
        _WecookJSBridge._handleMessageFromJs(message.data||null,message.responseId||null,
            message.responseData||null,message.callbackId||null,message.handlerName||null);
    }

    function _dispatchMessageFromNative(messageJSON) {
        var message = JSON.parse(messageJSON);
        var messageHandler;

        if (message.responseId) {
            var responseCallback = responseCallbacks[message.responseId];
            if (!responseCallback) { return; }
            responseCallback(message.responseData);
            delete responseCallbacks[message.responseId];
        } else {
            var responseCallback;
            if (message.callbackId) {
                var callbackResponseId = message.callbackId;
                responseCallback = function(responseData) {
                    _doSend({ responseId:callbackResponseId, responseData:responseData });
                }
            }

            var handler = WecookJSBridge._messageHandler;
            if (message.handlerName) {
                handler = messageHandlers[message.handlerName];
            }
            try {
                handler(message.data, responseCallback);
            } catch(exception) {
                if (typeof console != 'undefined') {
                    console.log("WecookJSBridge: WARNING: javascript handler threw.", message, exception);
                }
            }
        }
    }

	window.WecookJSBridge = {
		init: init,
		call: send,
		on: registerHandler,
		invoke: callHandler,
		_handleMessageFromNative: _handleMessageFromNative
	};

	var doc = document;
    var readyEvent = doc.createEvent('Events');
    readyEvent.initEvent('WecookJSBridgeReady');
    readyEvent.bridge = WecookJSBridge;
    doc.dispatchEvent(readyEvent);
})();