import { TreeUtils } from '../index'

/**
 * 数组转树形结构
 * @param list 源数组
 * @param tree 树
 * @param parentId 父ID
 */
export function listToTree (list, tree, parentId) {
    list.forEach(item => {
      // 判断是否为父级
      if (item.parentId === parentId) {
        const child = {
          ...item,
          children: []
        }
        // 迭代 list， 找到当前菜单相符合的所有子集
        listToTree(list, child.children, item.id)
        // 删掉不存在 children 值的属性
        if (child.children.length <= 0) {
          delete child.children
        }
        // 加入到树中
        tree.push(child)
      }
    })
  }

  export default TreeUtils
