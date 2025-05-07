/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myQAGRTP;

import java.util.ArrayList;

/**
 *
 * @author
 */
public class TestSuite {
	private String testLabel;
	private ArrayList<Integer> nodes;

	public TestSuite() {
		testLabel = " ";
		nodes = new ArrayList<>();
	}

	public TestSuite(String testLabel, ArrayList<Integer> nodes) {
		this.testLabel = testLabel;
		this.nodes = nodes;
	}

	public String getTestLabel() {
		return testLabel;
	}

	public void setTestLabel(String testLabel) {
		this.testLabel = testLabel;
	}

	public ArrayList<Integer> getNodes() {
		return nodes;
	}

	public void updateNodes(int node) {
		nodes.add(node);
	}

	@Override
	public String toString() {
		String test = "Test case " + testLabel;
		String ids = "";
		for (Integer id : nodes) {
			if (ids.length() > 0) {
				ids += ", " + id;
			} else {
				ids += id;
			}
		}
		String tcase = test.trim() + ": " + ids;
		return tcase;
	}

}
