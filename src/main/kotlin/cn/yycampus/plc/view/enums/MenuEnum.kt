package cn.yycampus.plc.view.enums

import javax.swing.Icon
import javax.swing.JMenu
import javax.swing.JMenuItem

enum class MenuEnum(val title: String, val icon: Icon? = null, val parent: MenuEnum? = null) {

    FILE("File", null, null),
    NEW("New", null, FILE),

    WorkSpace("Workspace...", null, NEW),

    OPEN("Open", null, FILE),
    SAVE("Save", null, FILE),
    SAVE_AS("Save as", null, FILE),
    ;

    data class MenuTree(val actionEnum: MenuEnum, val child: List<MenuTree>)
    companion object {
        private fun buildTree(parent: MenuEnum?): List<MenuTree> {
            return entries.filter { it.parent == parent }.map {
                MenuTree(it, buildTree(it))
            }
        }

        @JvmStatic
        fun buildMenu(
            parent: MenuEnum?,
            menu: JMenu?,
            click: (action: MenuEnum) -> Unit = {}
        ): List<JMenu?> {
            return buildTree(parent).map {
                if (it.child.isEmpty() && parent != null) {
                    val menuItem = JMenuItem(it.actionEnum.title)
                    menuItem.addActionListener { _ -> click(it.actionEnum) }
                    menu?.add(menuItem)
                    null
                } else {
                    val jmenu = JMenu(it.actionEnum.title)
                    menu?.add(jmenu)
                    buildMenu(it.actionEnum, jmenu, click)
                    jmenu
                }

            }
        }
    }
}