package ru.android.pictureoftheday.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName
import java.util.*

@JsonIgnoreProperties(ignoreUnknown=true)
data class PictureOfEarthOnDateResponse(
    @SerializedName("attitude_quaternions")
    val attitudeQuaternions: AttitudeQuaternions,
    val caption: String,
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,
    val coords: Coords,
    val date: String,
    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position: DscovrJ2000Position,
    val identifier: String,
    @SerializedName("image")
    val imageName: String,
    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position: LunarJ2000Position,
    @SerializedName("sun_j2000_position")
    val sunJ2000Position: SunJ2000Position,
    val version: String
) {
    data class AttitudeQuaternions(
        val q0: Double,
        val q1: Double,
        val q2: Double,
        val q3: Double
    )

    data class CentroidCoordinates(
        val lat: Double,
        val lon: Double
    )

    data class Coords(
        @SerializedName("attitude_quaternions")
        val attitudeQuaternions: AttitudeQuaternions,
        @SerializedName("centroid_coordinates")
        val centroidCoordinates: CentroidCoordinates,
        @SerializedName("dscovr_j2000_position")
        val dscovrJ2000Position: DscovrJ2000Position,
        @SerializedName("lunar_j2000_position")
        val lunarJ2000Position: LunarJ2000Position,
        @SerializedName("sun_j2000_position")
        val sunJ2000Position: SunJ2000Position
    ) {
        data class AttitudeQuaternions(
            val q0: Double,
            val q1: Double,
            val q2: Double,
            val q3: Double
        )

        data class CentroidCoordinates(
            val lat: Double,
            val lon: Double
        )

        data class DscovrJ2000Position(
            val x: Double,
            val y: Double,
            val z: Double
        )

        data class LunarJ2000Position(
            val x: Double,
            val y: Double,
            val z: Double
        )

        data class SunJ2000Position(
            val x: Double,
            val y: Double,
            val z: Double
        )
    }

    data class DscovrJ2000Position(
        val x: Double,
        val y: Double,
        val z: Double
    )

    data class LunarJ2000Position(
        val x: Double,
        val y: Double,
        val z: Double
    )

    data class SunJ2000Position(
        val x: Double,
        val y: Double,
        val z: Double
    )
}