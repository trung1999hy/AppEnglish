/* NAM NV created on 22:05 12-4-2023 */
package com.example.englishttcm

interface OnActionCallback {
    fun backToPrevious()
    fun showFragment(fromFragment: Class<*>, toFragment: Class<*>, data: Any? = null, isBack: Boolean = false)
}