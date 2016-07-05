# ZXing Android Embedded

Barcode scanning library for Android, using [ZXing][2] for decoding.

The project is loosely based on the [ZXing Android Barcode Scanner application][2], but is not affiliated with the official ZXing project.

Features:

1. Can be used via Intents (little code required).
2. Can be embedded in an Activity, for advanced customization of UI and logic.
3. Scanning can be performed in landscape or portrait mode.
4. Camera is managed in a background thread, for fast startup time.

A sample application is available in [Releases](https://github.com/journeyapps/zxing-android-embedded/releases).

## Adding aar dependency with Gradle

From version 3 this is a single library, supporting Gingerbread and later versions of Android
(API level 9+). If you need support for earlier Android versions, use [version 2][4].

Add the following to your build.gradle file:
s
```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.journeyapps:zxing-android-embedded:3.3.0@aar'
    compile 'com.google.zxing:core:3.2.1'
    compile 'com.android.support:appcompat-v7:23.1.0'   // Version 23+ is required
}

android {
    buildToolsVersion '23.0.2' // Older versions may give compile errors
}

```

## Usage with IntentIntegrator

Launch the intent with the default options:
```java
new IntentIntegrator(this).initiateScan(); // `this` is the current Activity


// Get the results:
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if(result != null) {
        if(result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
```

Use from a Fragment:
```java
IntentIntegrator.forFragment(this).initiateScan(); // `this` is the current Fragment

// If you're using the support library, use IntentIntegrator.forSupportFragment(this) instead.
```

Customize options:
```java
IntentIntegrator integrator = new IntentIntegrator(this);
integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
integrator.setPrompt("Scan a barcode");
integrator.setCameraId(0);  // Use a specific camera of the device
integrator.setBeepEnabled(false);
integrator.setBarcodeImageEnabled(true);
integrator.initiateScan();
```

See [IntentIntegrator][5] for more options.

### Changing the orientation

To change the orientation, specify the orientation in your `AndroidManifest.xml` and let the `ManifestMerger` to update the Activity's definition.

Sample:

```xml
<activity
		android:name="com.journeyapps.barcodescanner.CaptureActivity"
		android:screenOrientation="fullSensor"
		tools:replace="screenOrientation" />
```

```java
IntentIntegrator integrator = new IntentIntegrator(this);
integrator.setOrientationLocked(false);
integrator.initiateScan();
```

### Customization and advanced options

See [EMBEDDING](EMBEDDING.md).

For more advanced options, look at the [Sample Application](https://github.com/journeyapps/zxing-android-embedded/blob/master/sample/src/main/java/example/zxing/MainActivity.java),
and browse the source code of the library.

## Android Permissions

The camera permission is required for barcode scanning to function. It is automatically included as
part of the library. On Android 6 it is requested at runtime when the barcode scanner is first opened.

When using BarcodeView directly (instead of via IntentIntegrator / CaptureActivity), you have to
request the permission manually before calling `BarcodeView#resume()`, otherwise the camera will
fail to open.

## Building locally

    ./gradlew assemble

To deploy the artifacts the your local Maven repository:

    ./gradlew publishToMavenLocal

You can then use your local version by specifying in your `build.gradle` file:

    repositories {
        mavenLocal()
    }

## Sponsored by

[JourneyApps][1] - Creating business solutions with mobile apps. Fast.


## License

[Apache License 2.0][7]


[1]: http://journeyapps.com
[2]: https://github.com/zxing/zxing/
[3]: https://github.com/zxing/zxing/wiki/Scanning-Via-Intent
[4]: https://github.com/journeyapps/zxing-android-embedded/blob/2.x/README.md
[5]: zxing-android-embedded/src/com/google/zxing/integration/android/IntentIntegrator.java
[7]: http://www.apache.org/licenses/LICENSE-2.0



＝＝＝＝＝盛景智慧的使用＝＝＝＝＝＝
###1 添加依赖###
    compile 'com.journeyapps:zxing-android-embedded:3.2.0@aar'
    compile 'com.google.zxing:core:3.2.1'
    
###2 通过继承activity，在manifest设置方向，固定扫码界面的方向；###
    
   ```
    public class ScanActivity extends CaptureActivity{

    //private DecoratedBarcodeView barcodeScannerView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.custom_scanner_layout);
    }
	}
   ```
   ### 3 自定义布局文件custom_scanner_layout
   
    ```
    <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.journeyapps.barcodescanner.DecoratedBarcodeView
        android:id="@+id/barcode_scanner"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_alignParentTop="true">

    </com.journeyapps.barcodescanner.DecoratedBarcodeView>

</RelativeLayout>
    
    ```
    将源文件中的DecoratedBarcodeView.java拷贝到工程中，修改属性相关到代码。
    
```
private void initialize(AttributeSet attrs) {
        // Get attributes set on view
//        TypedArray attributes = getContext().obtainStyledAttributes(attrs,R.styleable.zxing_view);

//        int scannerLayout = attributes.getResourceId(
//                R.styleable.zxing_view_zxing_scanner_layout, R.layout.zxing_barcode_scanner);

//        attributes.recycle();

        inflate(getContext(), R.layout.zxing_barcode_scanner, this);

        barcodeView = (BarcodeView) findViewById(R.id.zxing_barcode_surface);

        if (barcodeView == null) {
            throw new IllegalArgumentException(
                    "There is no a com.journeyapps.barcodescanner.BarcodeView on provided layout " +
                            "with the id \"zxing_barcode_surface\".");
        }

        // Pass on any preview-related attributes
//        barcodeView.initializeAttributes(attrs);


        viewFinder = (ViewfinderView) findViewById(R.id.zxing_viewfinder_view);

        if (viewFinder == null) {
            throw new IllegalArgumentException(
                    "There is no a com.journeyapps.barcodescanner.ViewfinderView on provided layout " +
                            "with the id \"zxing_viewfinder_view\".");
        }

        viewFinder.setCameraPreview(barcodeView);

        // statusView is optional
        statusView = (TextView) findViewById(R.id.zxing_status_view);
    }

```

拷贝zxing_barcode_scanner文件，修改内容，将上述修改到代码布局指向修改成工程下到文件。可以修改布局。原文件为

```

<?xml version="1.0" encoding="utf-8"?>
<merge xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <com.journeyapps.barcodescanner.BarcodeView
        android:id="@+id/zxing_barcode_surface"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.journeyapps.barcodescanner.ViewfinderView
        android:id="@+id/zxing_viewfinder_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <TextView
        android:id="@+id/zxing_status_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal|bottom"
        android:layout_marginBottom="80dp"
        android:layout_marginLeft="22dp"
        android:layout_marginRight="22dp"
        android:background="@color/zxing_transparent"
        android:lineSpacingExtra="15dp"
        android:text="@string/zxing_msg_default_status"
        android:textColor="@color/zxing_status_text" />
</merge>

```
    
    
