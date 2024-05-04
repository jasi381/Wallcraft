package com.jasmeet.wallcraft.model.navigationDrawer

enum class DrawerState {
    OPEN,
    CLOSE
}

fun DrawerState.isOpened(): Boolean {
    return this == DrawerState.OPEN
}

fun DrawerState.opposite(): DrawerState {
    return if (this == DrawerState.OPEN) DrawerState.CLOSE else DrawerState.OPEN

}