package com.utils.Solr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;
import org.junit.Test;

public class PinyinTest {

	@Test
	public void pinyin() throws SolrServerException {
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.186:8081/solr/VSHOP");

		SolrQuery params = new SolrQuery();
		params.setQuery("紧身");//j,ji,jin,jins,jinsh,jinshe,jinshen
		params.set("df", "pinyin");
		params.setStart(0);
		params.setRows(10000);
		params.setFields("productionName");
		
		
		params.setParam(GroupParams.GROUP, true);
		params.setParam(GroupParams.GROUP_FIELD, "productionId");
		params.setParam(GroupParams.GROUP_LIMIT, "1");
		params.setParam(GroupParams.GROUP_TOTAL_COUNT, true);

		QueryResponse response = solrServer.query(params);
		GroupResponse gresp = response.getGroupResponse();// 注意：此处不能再用resp.getResults()接收结果
		List<GroupCommand> commands = gresp.getValues();

		if (commands != null) {
			for (GroupCommand com : commands) {
				System.out.println("总的分组个数：" + com.getNGroups().longValue());
				for (Group group : com.getValues()) {
					// 9、结果集
					SolrDocumentList results = group.getResult();
					// 12、封装返回值
					for (SolrDocument doc : results) {
						System.out.println("==>" + doc);
					}
				}
			}
		}
	}
	@Test
	public void test2List() throws Exception {
		
		List<String> list1=new ArrayList<>();
		List<String> list2=new ArrayList<>();
		list1.add("list1-1");
		list1.add("list1-2");
		list2.add("list2-1");
		list2.add("list2-2");
		list2.addAll(list1);
		
		for (String string : list2) {
			System.out.println(string);
		}
	}
}
