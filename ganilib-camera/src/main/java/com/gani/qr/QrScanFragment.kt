package com.gani.qr

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.gani.lib.R
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.screen.GScreenContainer
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
    private lateinit var qrView: SurfaceView

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (checkPermission()) {
            cameraSource.start(qrView.holder)
        }
    }

    override fun initContent(activity: GActivity, container: GScreenContainer) {
//        disableRefreshPull()

        LayoutInflater.from(activity).inflate(R.layout.qr_code_layout, container.content, true)
        qrView = container.content.findViewById(R.id.qr_view)

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

                    qrView.post {
                        cameraSource.stop()
                        onScan(value)
                    }
                }
            }
        })



        qrView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

//                qrView.setOnClickListener { cameraFocus(cameraSource, Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO) }

                if (checkPermission()) {
                    cameraSource.start(qrView.holder)
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


    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(Res.context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    open abstract fun onScan(value: String);
}