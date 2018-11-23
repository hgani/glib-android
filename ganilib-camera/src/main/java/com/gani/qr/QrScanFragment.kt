package com.gani.qr

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.SurfaceHolder
import com.gani.lib.screen.GFragment
import com.gani.lib.utils.Res
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector


abstract class QrScanFragment : GFragment() {
    companion object {
        val PERMISSION_CAMERA = 1;
    }

    //    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    protected lateinit var scannerView: GSurfaceView

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (checkPermission()) {
            cameraSource.start(scannerView.holder)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSurface(context!!)
    }

    private fun initSurface(context: Context): GSurfaceView {
        scannerView = GSurfaceView(context)

        val detector = BarcodeDetector.Builder(activity)
                .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
                .build()

        cameraSource = CameraSource.Builder(context, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .build()

        detector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() > 0) {
                    val value = barcodes.valueAt(0).displayValue

                    scannerView.post {
                        cameraSource.stop()
                        onScan(value)
                    }
                }
            }
        })

        scannerView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

//                scannerView.setOnClickListener { cameraFocus(cameraSource, Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO) }

                if (checkPermission()) {
                    cameraSource.start(scannerView.holder)
                } else {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
                }
            }

//            private fun cameraFocus(cameraSource: CameraSource, focusMode: String): Boolean {
//                val declaredFields = CameraSource::class.java.declaredFields
//
//                for (field in declaredFields) {
//                    if (field.type == Camera::class.java) {
//                        field.isAccessible = true
//                        try {
//                            val camera = field.get(cameraSource) as Camera
//                            if (camera != null) {
//                                val params = camera.parameters
//                                params.focusMode = focusMode
//                                camera.parameters = params
//                                return true
//                            }
//
//                            return false
//                        } catch (e: IllegalAccessException) {
//                            e.printStackTrace()
//                        } catch (e: NullPointerException) {
//                            GLog.e(javaClass, "null pointer $e")
//                        }
//
//                        break
//                    }
//                }
//
//                return false
//            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.release()
            }
        })

        return scannerView
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(Res.context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    abstract fun onScan(value: String);
}