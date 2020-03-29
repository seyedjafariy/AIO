package com.worldsnas.daggercore.lifecycle

enum class Permissions(
    val rawValue: String
) {
    READ_STORAGE(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ),
    ;

    companion object {
        @JvmStatic
        fun fromRawValue(raw: String) =
            values()
                .find { it.rawValue == raw }
                ?: throw IllegalArgumentException("not a real permission= $raw")

    }
}

enum class PermissionsResult(
    val rawValue: Int
) {
    GRANTED(
        android.content.pm.PackageManager.PERMISSION_GRANTED
    ),
    DENIED(
        android.content.pm.PackageManager.PERMISSION_DENIED
    ),
    ;

    companion object {
        @JvmStatic
        fun fromRawValue(raw: Int) =
            values()
                .find { it.rawValue == raw }
                ?: throw IllegalArgumentException("not a real permissionResult= $raw")

    }
}
