/* NAM NV created on 22:05 12-4-2023 */
package com.tpk.englishttcm

interface OnActionCallback {
    fun backToPrevious()
    fun showFragment(fromFragment: Class<*>, toFragment: Class<*>, enterAnim: Int, exitAnim: Int, data: Any? = null, isBack: Boolean = false)
}