/*
 *  This file is part of the SIRIUS library for analyzing MS and MS/MS data
 *
 *  Copyright (C) 2013-2015 Kai Dührkop
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with SIRIUS.  If not, see <http://www.gnu.org/licenses/>.
 */
package fragtreealigner.domainobjects.graphs;

import fragtreealigner.algorithm.ScoringFunctionNeutralLosses;
import fragtreealigner.domainobjects.chem.components.NeutralLoss;
import fragtreealigner.util.Session;

@SuppressWarnings("serial")
public class AlignmentTree extends Tree<AlignmentTreeNode, AlignmentTreeEdge> {
	private FragmentationTree correspondingFragTree;
	private float selfAligScore;
	
	public AlignmentTree() {
		super(null);
	}
	
	public AlignmentTree(Session session) {
		super(session);
	}
	
	public AlignmentTree(FragmentationTree correspondingFragTree, Session session) {
		super(session);
		this.correspondingFragTree = correspondingFragTree;
	}
	
	public FragmentationTree getCorrespondingFragTree() {
		return correspondingFragTree;
	}
	
	public void setSelfAligScore(float selfAligScore) {
		this.selfAligScore = selfAligScore;
	}

	public float getSelfAligScore() {
		if (selfAligScore == 0){
			ScoringFunctionNeutralLosses sFuncNL = new ScoringFunctionNeutralLosses(session);
			for (AlignmentTreeNode node : nodes){
				selfAligScore += sFuncNL.score(node, node);
			}
		}
		return selfAligScore;
	}

	@Override
	public AlignmentTreeNode addNode(String label) {
		AlignmentTreeNode node = new AlignmentTreeNode(label);
		super.addNode(node, label);
		return node;
	}
	
	public AlignmentTreeNode addNode(String label, NeutralLoss neutralLoss) {
		AlignmentTreeNode node = new AlignmentTreeNode(label, neutralLoss);
		super.addNode(node, label);
		return node;
	}

	public AlignmentTreeNode addNode(String label, NeutralLoss neutralLoss, double weight) {
		AlignmentTreeNode node = new AlignmentTreeNode(label, neutralLoss, weight);
		super.addNode(node, label);
		return node;
	}

	@Override
	public AlignmentTreeEdge connect(AlignmentTreeNode parent, AlignmentTreeNode child) {
		AlignmentTreeEdge edge = new AlignmentTreeEdge(parent, child);
		super.connect(edge);
		return edge;
	}

	public AlignmentTreeEdge connect(AlignmentTreeNode parent, AlignmentTreeNode child, String label) {
		AlignmentTreeEdge edge = new AlignmentTreeEdge(parent, child, label);
		super.connect(edge);
		return edge;
	}

	@Override
	public AlignmentTreeNode parseNode(String label, String parameters) {
//		TODO Fuer neutral loss Masse anpassen
		String[] params = parameters.split("\\s+");
		NeutralLoss neutralLoss = new NeutralLoss(params[0], 0.0, session);
		return addNode(label, neutralLoss);
	}
	
	@Override
	public AlignmentTree clone() {
		AlignmentTree clonedAligTree = new AlignmentTree(correspondingFragTree, session);
		buildUpClonedGraph(clonedAligTree);
		return clonedAligTree;
	}

	protected void buildUpClonedGraph(AlignmentTree clonedGraph) {
		super.buildUpClonedGraph(clonedGraph);
	}
}
