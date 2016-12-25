package com.kaishengit.service;

import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 * 帖子的业务处理层
 */
public class TopicService {

    private UserDao userDao = new UserDao();
    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();
    private ReplyDao replyDao = new ReplyDao();
    private FavDao favDao = new FavDao();
    private ThankDao thankDao = new ThankDao();
    Logger logger = LoggerFactory.getLogger(TopicService.class);

    /**
     * 查找所有节点
     */
    public List<Node> findAllNodeService() {
        return nodeDao.findAllNode();
    }

    /**
     * 添加主题帖子，并且在帖子的节点topicnum+1
     *
     * @param title
     * @param content
     * @param nodeid
     * @param userid
     * @return
     */
    public Integer saveTopic(String title, String content, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        //暂时设置最后一次回复时间为当前时间，要不刚刚建表显示空
        topic.setLastreplytime(new Timestamp(new Date().getTime()));
        Integer topicid = topicDao.save(topic);
        //帖子保存完毕后，node节点中topicnum+1
        Node node = nodeDao.findNodeById(nodeid);//为了严谨判断一下此节点是否存在
        if (node != null) {
            node.setTopicnum(node.getTopicnum() + 1);
            nodeDao.updateNode(node);
        } else {
            throw new ServiceException("此节点不存在");
        }
        //保存同步到数据库update

        return topicid;
    }

    /**
     * 通过topicid找到需要展示的主题对象，并且把user和node的值当属性给他
     *
     * @param topicid
     */
    public Topic findByTopicId(String topicid) {
        //通过topicid找到topic对象，获取两个外键，在获取两个对象，封装给topic
        if (StringUtils.isNumeric(topicid)) {//地址栏传值，会被改变，必须是数字，也可能找不到
            Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
            if (topic != null) {
                User user = userDao.findById_User(topic.getUserid());
                Node node = nodeDao.findNodeById(topic.getNodeid());
                topic.setUser(user);
                topic.setNode(node);
                return topic;
            } else {
                logger.error("该帖子不存在或者已经被删除");
                throw new ServiceException("该帖子不存在或者已经被删除");
            }

        } else {
            logger.error("topicid不是数字");
            throw new ServiceException("topicid不是数字");
        }

    }
    /**
     *   添加回复，并且更新topic最后一次回复时间，replynum
     */

    public void saveTopicReply(String content, String topicid, User user) {
        if(user != null){
            if(StringUtils.isNumeric(topicid)){
                Reply reply = new Reply();
                reply.setContent(content);
                reply.setTopicid(Integer.valueOf(topicid));
                reply.setUserid(user.getId());
                replyDao.saveReply(reply);
                Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
                if(topic != null){
                    //更新帖子的回复数量,和最后一次回复时间
                    topic.setReplynum(topic.getReplynum()+1);
                    topic.setLastreplytime(new Timestamp(new Date().getTime()));//就用现在的时间就行，不必去数据库查发帖时间
                    topicDao.updateTopic(topic);
                } else {
                    throw new ServiceException("主题不存在或已经被删除");
                }

            } else {
                logger.error("参数错误");
                throw new ServiceException("参数错误");
            }
        } else {
            logger.error("session过期或者错误");
            throw new ServiceException("session过期或者错误");
        }
    }

    //记录点击次数
    public void topicClickNum(Topic topic) {
        //topic一定不是null,在上面判断过
        topic.setClicknum(topic.getClicknum() +1);
        topicDao.updateTopic(topic);

    }

    //两表查询，reply和user,user封装给reply就返回reply类型
    public List<Reply> findReplyByTopicId(Integer topicid) {
        return replyDao.findReplyTopicId(topicid);

    }

    //修改主题，条件无回复，没超过50分钟
    public void updateTopic(String topicid, String title, String content, String nodeid) {

        if(StringUtils.isNumeric(topicid)){
            //因为需要在这里判断是否修改，超过规定没。也要在jsp修改，是否显示编辑吗。所以最好的做法，就是在jsp用EL表达式可以获取，所以在topic类创建一个方法，is或get开头，来判断是否可以显示编辑，在这里也可应通过对象调用。
            //调用topic里面的isEdit判断是否是可编辑，不能new，因为new出来属性没值，判断不出来，去查找用topicid
            Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
            if(topic.isEdit()){
                //判断是否改变节点了,没有改变节点就不用修改topicnum
                if(topic.getNodeid() != Integer.valueOf(nodeid)){
                    Node oldNode = nodeDao.findNodeById(topic.getNodeid());//原来的
                    Node newNode = nodeDao.findNodeById(Integer.valueOf(nodeid));//修改后新的
                    oldNode.setTopicnum(oldNode.getTopicnum() - 1);
                    newNode.setTopicnum(newNode.getTopicnum() + 1);
                    nodeDao.updateNode(oldNode);
                    nodeDao.updateNode(newNode);
                }
                topic.setTitle(title);
                topic.setContent(content);
                topic.setNodeid(Integer.valueOf(nodeid));
                topicDao.updateTopic(topic);

            } else {
                throw new ServiceException("已经回复或者超过规定时间不可修改");
            }
        } else {
            throw  new ServiceException("参数错误");
        }

    }

    /**
     * 判断是加入还是取消收藏
     * @param user
     * @param topicid
     */
    public Fav findFavByTopicIdAndUserId(User user, String topicid) {

        return favDao.findFavByUserIdAndTopicId(Integer.valueOf(topicid),user.getId());

    }

    /**
     * 收藏功能，加入收藏需要在关系表添加一条记录，z在topic表更新收藏数量favnum
     * @param topicid
     * @param action
     */
    public int favTopic(User user,String topicid, String action) {

        if ("fav".equals(action)) {
            //收藏
            favDao.saveFav(user.getId(), Integer.valueOf(topicid));
            Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
            if (topic != null) {
                topic.setFavnum(topic.getFavnum() + 1);
                topicDao.updateTopic(topic);
            } else {
                throw new ServiceException("您查找的用户不存在或者已经被删除");
            }
        } else if ("unfav".equals(action)) {
            //取消收藏.删除，关系表删除需要两个值,因为是多对多
            favDao.deleteFav(user.getId(), Integer.valueOf(topicid));
            Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
            if (topic != null) {
                topic.setFavnum(topic.getFavnum() - 1);
                topicDao.updateTopic(topic);
            } else {
                throw new ServiceException("您查找的用户不存在或者已经被删除");
            }

        } else {
            throw new ServiceException("参数错误");
        }
        //最后都查一下数据库，本主题有多少收藏，用查询去看。直接从数据库获取更为准确。
        Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
        return topic.getFavnum();
    }

    /**
     * 分页查询全部
     * @param pageNo
     * @return
     */
    //传分页查询全部帖子，按照最后回复时间排序
    public Page<Topic> findTopicAndUser(int pageNo) {
        //查看多少个帖子,总条数
        int totals = topicDao.findTopicCount();
        Page<Topic> page = new Page<>(totals,pageNo);
        List<Topic> topicList = topicDao.findTopicAndUserDao(page);
        page.setItems(topicList);
        return page;
    }

    /**
     * 分页查询指定节点
     * @param nodeid    节点，根据节点查指定节点的主题topic
     * @param pageNo    页号，根据页号查指定页面的主题
     */
    public Page findTopicAndUserByNodeId(Integer nodeid, int pageNo) {

        //根据nodeid查一下node如果存在再跳转，不存在说明错误
        Node node = nodeDao.findNodeById(nodeid);
        if(node == null){
            throw new ServiceException("参数错误");
        }

        //如果从地址栏修改，nodeid和pageNo大于已有页数，
        int totals = topicDao.findTopicCountByNodeId(nodeid);
        Page page = new Page(totals,pageNo);

        page.setItems(topicDao.findTopicAndUserByNodeIdDao(nodeid,page));
        return page;
    }

    /**
     * 查询这组感谢关系是否存在
     * @param userid
     * @param topicid
     * @return
     */
    public Thank finThankByTopicIdAndUserId(Integer userid, Integer topicid) {
        return topicDao.finThankByUsericIdAndTopicId(userid,topicid);
    }

    /**
     * 添加或者删除一条感谢关系，并且同步到topic表的thankyounum
     * @param user
     * @param topic
     * @param action
     */
    public Topic thankTopic(User user, Topic topic, String action) {
        //topic.getId()一定是不变的
        if("thank".equals(action)){
            //感谢
            thankDao.save(user.getId(),topic.getId());
            //同步到topic表的thankyounum
            topic.setThankyounum(topic.getThankyounum() + 1);
            topicDao.updateTopic(topic);
        } else if("unthank".equals(action)){
            //取消感谢
            thankDao.delete(user.getId(),topic.getId());
            topic.setThankyounum(topic.getThankyounum() - 1);
            topicDao.updateTopic(topic);
        }
        //最后都查一下topic表的thankyounum，再返回到jsp更加准确
        return topicDao.findTopicId(topic.getId());
    }
}
