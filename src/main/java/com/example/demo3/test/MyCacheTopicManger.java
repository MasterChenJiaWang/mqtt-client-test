// package com.example.demo3.test;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import com.unittec.customize.iot.mqtt.api.RsocketTopicManager;
// import com.unittec.customize.iot.mqtt.api.TransportConnection;
//
// import cn.hutool.cache.CacheUtil;
// import cn.hutool.cache.impl.LFUCache;
//
/// **
// * @Description:
// * @author: chenjiawang
// * @CreateDate: 2020/6/29 10:13
// */
//// @Component
// public class MyCacheTopicManger implements RsocketTopicManager {
//
// /**
// *
// */
// private static final LFUCache<String, List<TransportConnection>> CACHE_TOPIC =
// CacheUtil.newLFUCache(100, 100 * 60 * 1000);
//
// /**
// *
// * @param topic
// * @return
// */
// @Override
// public List<TransportConnection> getConnectionsByTopic(String topic) {
// System.out.println("topic: " + topic);
// return CACHE_TOPIC.get(topic);
// }
//
// @Override
// public void addTopicConnection(String topic, TransportConnection transportConnection) {
// System.out.println("addTopicConnection: " + topic);
// List<TransportConnection> transportConnections = CACHE_TOPIC.get(topic);
// if (transportConnections == null) {
// transportConnections = new ArrayList<>();
// }
// transportConnections.add(transportConnection);
// CACHE_TOPIC.put(topic, transportConnections);
// }
//
// @Override
// public void deleteTopicConnection(String topic, TransportConnection transportConnection) {
// System.out.println("deleteTopicConnection: " + topic);
// List<TransportConnection> transportConnections = CACHE_TOPIC.get(topic);
// if (transportConnections == null) {
// return;
// }
// transportConnections.remove(transportConnection);
// CACHE_TOPIC.put(topic, transportConnections);
// }
// }
