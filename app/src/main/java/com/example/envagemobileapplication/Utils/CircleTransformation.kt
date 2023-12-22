package com.example.envagemobileapplication.Utils

import android.graphics.*
import com.squareup.picasso.Transformation


class CircleTransformation : Transformation {
    override fun key() = "circle_with_stroke"

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        paint.shader = shader
        paint.isAntiAlias = true

        val radius = size / 2f

        // Draw the circular image
        canvas.drawCircle(radius, radius, radius, paint)

        // Draw a stroke around the circle
        val strokePaint = Paint()
        strokePaint.color = Color.GRAY // Change the color of the stroke
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = 2f // Change the width of the stroke

        canvas.drawCircle(radius, radius, radius - (strokePaint.strokeWidth / 2), strokePaint)

        squaredBitmap.recycle()
        return bitmap
    }
}