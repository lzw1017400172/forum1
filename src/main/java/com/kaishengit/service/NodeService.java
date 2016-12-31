package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
public class NodeService {


    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();


    /**
     * 根据nodeid查找node
     * @param nodeid
     */
    public Node findNodeByNodeId(Integer nodeid) {
        Node node = nodeDao.findNodeById(nodeid);
        if(node != null){
            return node;
        } else {
            throw new ServiceException("节点已经被删除或者不存在");
        }

    }

    /**
     * 远程验证修改的nodename
     * @param nodeid
     * @param nodename
     */
    public boolean validateNode(Integer nodeid, String nodename) {
        Node node = nodeDao.findNodeById(nodeid);
        if(node != null){
            //因为是修改，如果是新老名相同直接通过验证
            if(nodename.equals(node.getNodename())){
                return true;
            } else {
                Node nodes = nodeDao.findNodeByNodeName(nodename);
                if(nodes == null){
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            throw new ServiceException("节点不存在或者已经被删除");
        }

    }
    //重载
    public boolean validateNode(String nodename) {
        Node nodes = nodeDao.findNodeByNodeName(nodename);
        if(nodes == null){
            return true;
        } else {
            return false;
        }
    }
    /**
     * 修改节点名字
     * @param
     * nodeid
     * @param nodename
     */
    public void updateNodeName(Integer nodeid, String nodename) {
        Node node = nodeDao.findNodeById(nodeid);

        if(node != null){
            node.setNodename(nodename);
            nodeDao.updateNode(node);
        } else {
            throw new ServiceException("节点不存在或者已经被删除");
        }
    }

    /**
     * 删除几点node根据id
     * @param nodeid
     */
    public void deleteNode(Integer nodeid) {
        Node node = nodeDao.findNodeById(nodeid);
        if(node != null){
            //node表在其他表上面是有外键的。不能轻易删除，首先判断此节点是否有主题。可以用node.getTopicnum();.
            //更加严谨，再去topic表用nodeid查一次
            //topicDao.findTopicByNodeId(nodeid);
            if(node.getTopicnum() == 0) {
                nodeDao.deleteNodeById(nodeid);
            } else {
                throw new ServiceException("此节点仍有主题不能删除");
            }
        } else {
            throw new ServiceException("节点不存在或者已经被删除");
        }

    }

    /**
     * 添加新节点
     * @param nodename
     */
    public void saveNewNode(String nodename) {
        //已经经过远程验证,严谨在判断一次
        Node node = nodeDao.findNodeByNodeName(nodename);
        if(node == null){
            nodeDao.saveNode(nodename);
        } else {
            throw  new ServiceException("此节点已经存在");
        }

    }

}
