package com.worldsnas.daggercore.lifecycle

sealed class LifecycleEvent {
    data class Permission(
        val requestCode: Int,
        val permissions: List<Permissions>,
        val results: List<PermissionsResult>
    ) : LifecycleEvent()
}
