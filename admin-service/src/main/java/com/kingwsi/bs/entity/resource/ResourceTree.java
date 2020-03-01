package com.kingwsi.bs.entity.resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceTree {
    private List<ResourceNode> menuList = new ArrayList<>();

    public ResourceTree(List<ResourceNode> menuList) {
        this.menuList = menuList;
    }

    //建立树形结构
    public List<ResourceNode> buildTree() {
        List<ResourceNode> treeResourceNodes = new ArrayList<>();
        for (ResourceNode resourceNode : getRootNode()) {
            resourceNode = buildChildTree(resourceNode);
            treeResourceNodes.add(resourceNode);
        }
        return treeResourceNodes;
    }

    //递归，建立子树形结构
    private ResourceNode buildChildTree(ResourceNode node) {
        List<ResourceNode> childResourceNodes = new ArrayList<>();
        for (ResourceNode resourceNode : menuList) {
            if (resourceNode.getParentId().equals(node.getId())) {
                childResourceNodes.add(buildChildTree(resourceNode));
            }
        }
        node.setChildren(childResourceNodes);
        return node;
    }

    //获取根节点
    private List<ResourceNode> getRootNode() {
        List<ResourceNode> rootResourceNodeLists = new ArrayList<>();
        for (ResourceNode resourceNode : menuList) {
            if (resourceNode.getParentId().equals("-1")) {
                rootResourceNodeLists.add(resourceNode);
            }
        }
        return rootResourceNodeLists;
    }
}
