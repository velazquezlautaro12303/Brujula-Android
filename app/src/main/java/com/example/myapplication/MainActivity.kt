package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var imageView: ImageView
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var magnetometer: Sensor

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null
    private var i: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById(R.id.aguja)

        sensorManager =getSystemService(SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
        }


        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        println("Hola Mundo!")
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = p0.values
        } else if (p0!!.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = p0.values
        }
        /* if (gravity != null && geomagnetic != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)

            // Obtener la matriz de rotación
            if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation)

                // Obtener los grados del norte
                imageView.rotation = Math.toDegrees(orientation[0].toDouble()).toFloat()
            }
        } */
        // imageView.rotation = i++
        imageView.rotation = gravity!!.get(1) * 10
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}