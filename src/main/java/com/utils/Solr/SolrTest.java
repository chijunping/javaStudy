package com.utils.Solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrTest {

	@Test
	public void findDocInfoBySolr() throws Exception {

		HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8081/solr/VSHOP");
		String keyword = "*裙*";
		String MM = "80%";

		SolrQuery query = new SolrQuery();

		// 设置edismax的权重值
		query.set("defType", "edismax");
		query.set("fl", "*,score");

		query.setSort("putawayDate", ORDER.desc);
		query.setSort("score", ORDER.desc);
		List<SortClause> clauseList = new ArrayList<>();
		SortClause clause1 = new SortClause("putawayDate", ORDER.desc);
		SortClause clause2 = new SortClause("score", ORDER.desc);
		clauseList.add(clause2);
		clauseList.add(clause1);
		query.setSorts(clauseList);

		// 设置标题的最小匹配的百分比
		if (StringUtils.isNotBlank(MM)) {
			query.set("mm", MM + "%");
		} else {
			query.set("mm", "60%");
		}

		query.setQuery(keyword);
		query.set("df", "all");
		query.setStart(0);
		query.setRows(100);
		query.setFields("id","productionId", "productionName", "classifyName", "putawayDate", "normalPrice", "score");

		query.setHighlight(true); // 开启高亮组件
		query.addHighlightField("classifyName"); // 高亮字段
		query.addHighlightField("productionName"); // 给标题也添加高亮
		query.setHighlightSimplePre("<font style=\"color:#006600;\">"); // 标记
		query.setHighlightSimplePost("</font>");
		query.setHighlightSnippets(2); // 结果分片数，默认为1
		query.setHighlightFragsize(300); // 每个分片的最大长度，默认为100

		query.setFacet(true);// 设置facet=on
		query.addFacetField("productionId");// 设置需要facet的字段
		// query.addFacetField("productionId", "productionName",
		// "classifyName","putawayDate");// 设置需要facet的字段
		query.setFacetLimit(100);// 限制facet返回的数量
		query.setFacetMissing(false);// 不统计null的值
		query.setFacetMinCount(1);// 设置返回的数据中每个分组的数据最小值，比如设置为1，则统计数量最小为1，不然不显示
		// query.addFacetQuery("publishDate:[2014-04-11T00:00:00Z TO 2014-04-13T00:00:00Z]");

		solrServer.setSoTimeout(15000);
		solrServer.setConnectionTimeout(1000);
		solrServer.setDefaultMaxConnectionsPerHost(1000);
		solrServer.setMaxTotalConnections(1000);

		QueryResponse response = solrServer.query(query);
		// 查询耗时
		int qTime = response.getQTime();

		List<FacetField> facets = response.getFacetFields();// 返回的facet列表
		for (FacetField facet : facets) {
			System.out.println("facet名字：" + facet.getName());
			List<Count> counts = facet.getValues();
			for (Count count : counts) {
				System.out.println(count.getName() + ":" + count.getCount());
			}
		}

		// 获取返回的结果
		SolrDocumentList docList = response.getResults();
		// 获取文档总数
		long count = docList.getNumFound();
		System.out.println("查询结果总是：" + count);
		
		// 获取高亮信息
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

		for (SolrDocument doc : docList) {
			System.out.println("=================返回结果================");
			System.out.println("productionId=" + doc.getFieldValue("productionId"));
			List<String> list = highlighting.get(doc.get("id")).get("productionName");
			if(list!=null&&list.size()>0){
				System.out.println("productionName=" + list.get(0));
			}
			System.out.println("productionName=" + doc.getFieldValue("productionName"));
			System.out.println("classifyName=" + doc.getFieldValue("classifyName"));
			System.out.println("normalPrice=" + doc.getFieldValue("normalPrice"));
			System.out.println("score=" + doc.getFieldValue("score"));
			System.out.println("putawayDate=" + doc.getFieldValue("putawayDate"));
		}
		System.out.println(highlighting);
	}
	
	@Test
	public void testURI(){

		HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8081/solr/VSHOP");
		String keyword = "*裙*";
		String MM = "80%";

		SolrQuery query = new SolrQuery();

		// 设置edismax的权重值
		query.set("defType", "edismax");
		query.set("fl", "*,score");
		
	}

}
