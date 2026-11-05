package com.domed.util;

import com.domed.domain.cascaderCommon.CommonTree;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class CascaderUtil {

    public static List<CommonTree> genTree(List<CommonTree> treeList){
        List<CommonTree> trees = new ArrayList<>();
        for (CommonTree tree : treeList){
            if ("J00001".equals(tree.getParentCode()) ){
                trees.add(findChildren(tree,treeList));
            }
        }
        return  trees;
    }
    public static  CommonTree findChildren(CommonTree tree,List<CommonTree> treeList){
        for (CommonTree node : treeList){
            if (tree.getValue().equals(node.getParentCode())){
                if (tree.getChildren() == null){
                    tree.setChildren(new ArrayList<>());
                }
                tree.getChildren().add(findChildren(node,treeList));
            }
        }
        return tree;
    }
}
