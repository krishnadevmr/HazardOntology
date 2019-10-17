/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.HazardAnalysis.Graph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author kmoothandas
 */
public class HazardExpansionGraph extends JFrame {

    public HazardExpansionGraph(Integer hazardId) throws SQLException {
        super("Hazard Expansion");
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_SHADOW, true);
        style.put(mxConstants.ALIGN_LEFT, true);
        //style.put(mxConstants.STYLE_FONTCOLOR, "#eef2ed");
        style.put(mxConstants.STYLE_AUTOSIZE, 1);
        style.put(mxConstants.STYLE_FONTSIZE, 14);
        style = graph.getStylesheet().getDefaultEdgeStyle();
        style.put(mxConstants.STYLE_STROKECOLOR, "black");
        style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
        style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.SegmentConnector);
        style.put(mxConstants.STYLE_EDGE, mxConstants.STYLE_ENDARROW="none");
        graph.getModel().beginUpdate();
        HashMap<String, Object> diagram = new HashMap<String, Object>();
        try {
            ResultSet rs = DataBaseConnection.sql(
                    "SELECT * FROM hazardexpansion where hazardid =" + hazardId);
            Object r = null;
            while (rs.next()) {
                String rootKind = rs.getString("rootkind");
                String rootRole = rs.getString("rootrole");

                Object rr = null;
                if (!diagram.containsKey(rootRole)) {
                    rr = graph.insertVertex(parent, null, rootRole, 100, 100, 120, 40, "fillColor=#C4C4C4;");
                    diagram.put(rootRole, rr);
                    //graph.insertEdge(parent, null, "", diagram.get(rootRole), r);
                }
                if (!diagram.containsKey(rootKind)) {
                    Object k = graph.insertVertex(parent, null, rootKind, 100, 100, 120, 40, "fillColor=#800080;fontColor=#eef2ed;");
                    diagram.put(rootKind, k);
                    //graph.insertEdge(parent, null, "", diagram.get(rootKind), diagram.get(rootRole));
                }

                Object edge_rk_rr = null;
                if (!diagram.containsKey(rootKind + rootRole)) {
                    edge_rk_rr = graph.insertEdge(parent, null, "play", diagram.get(rootKind), diagram.get(rootRole), "defaultEdge;verticalAlign=top;verticalLabelPosition=bottom");
                    diagram.put(rootKind + rootRole, edge_rk_rr);
                }

                String rel = rs.getString("relator");
                if (!diagram.containsKey(rel)) {
                    r = graph.insertVertex(parent, null, rel, 100, 100, 120, 40, "fillColor=white;");
                    diagram.put(rel, r);
                }

                Object edge_rr_rel;
                if (!diagram.containsKey(rootRole + rel)) {
                    edge_rr_rel = graph.insertEdge(parent, null, "", diagram.get(rootRole), diagram.get(rel));
                    diagram.put(rootRole + rel, edge_rr_rel);
                }

                String linkedRole = rs.getString("linkedrole");
                Object lr;
                if (!diagram.containsKey(linkedRole + "link")) {
                    lr = graph.insertVertex(parent, null, linkedRole, 100, 100, 120, 40, "fillColor=#C4C4C4;");
                    diagram.put(linkedRole + "link", lr);
                }

                Object edge_rel_lr;
                if (!diagram.containsKey(linkedRole + "link" + rel)) {
                    edge_rel_lr = graph.insertEdge(parent, null, "", diagram.get(rel), diagram.get(linkedRole + "link"));
                    diagram.put(linkedRole + "link" + rel, edge_rel_lr);
                }

                String linkedKind = rs.getString("linkedkind");
                Object lk;
                if (!diagram.containsKey(linkedKind + "link")) {
                    lk = graph.insertVertex(parent, null, linkedKind, 100, 100, 120, 40, "fillColor=#800080;fontColor=#eef2ed;");
                    diagram.put(linkedKind + "link", lk);
                }

                Object edge_rl_rk;
                if (!diagram.containsKey(linkedRole + "link" + linkedKind + "link")) {
                    edge_rl_rk = graph.insertEdge(parent, null, "play", diagram.get(linkedKind + "link"), diagram.get(linkedRole + "link"));
                    diagram.put(linkedRole + "link" + linkedKind + "link", edge_rl_rk);
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }
        mxHierarchicalLayout graphComponent = new mxHierarchicalLayout(graph);
        graphComponent.execute(parent);
        mxGraphComponent graphComponents = new mxGraphComponent(graphComponent.getGraph());
        getContentPane().add(graphComponents);
    }
}
