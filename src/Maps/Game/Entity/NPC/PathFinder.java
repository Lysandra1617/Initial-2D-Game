package Maps.Game.Entity.NPC;

import Markers.Area;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import static Utilities.UT_Draw.drawRectangle;
import static java.lang.Float.POSITIVE_INFINITY;
import static java.lang.Math.*;

public class PathFinder {
    public static class Node {
        float f = POSITIVE_INFINITY;
        float g = POSITIVE_INFINITY;
        float h = POSITIVE_INFINITY;
        public int row = -1;
        public int col = -1;
        boolean obstacle = false;
        Node prev;
        ArrayList<Node> neighbors;

        public Node() {
            neighbors = new ArrayList<>();
            prev = null;
        }

        void reset() {
            f = POSITIVE_INFINITY;
            g = POSITIVE_INFINITY;
            h = POSITIVE_INFINITY;
            prev = null;
        }
    }
    Node begin;
    Node end;
    Node[][] nodes;
    ArrayList<Node> path;
    ArrayList<Node> searching = new ArrayList<>();
    ArrayList<Node> processed = new ArrayList<>();

    public PathFinder(Area[][] areas) {
        setNodes(areas);
    }

    public void setBegin(int row, int column) {
        begin = nodes[row][column];
    }

    public void setEnd(int row, int column) {
        end = nodes[row][column];
    }

    public void setNodes(Area[][] areas) {
        path = new ArrayList<>();
        int rows = areas.length;
        int cols = areas[0].length;
        nodes = new Node[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                nodes[r][c] = new Node();
                nodes[r][c].row = r;
                nodes[r][c].col = c;

                if (areas[r][c] != null) nodes[r][c].obstacle = true;
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r > 0) nodes[r][c].neighbors.add(nodes[r-1][c]);
                if (c > 0) nodes[r][c].neighbors.add(nodes[r][c-1]);
                if (r < rows - 1) nodes[r][c].neighbors.add(nodes[r+1][c]);
                if (c < cols - 1) nodes[r][c].neighbors.add(nodes[r][c+1]);
            }
        }
    }

    public void solve() {
        if (begin == null || end == null) return;
        searching.clear();
        processed.clear();

        for (Node[] nodeRow : nodes)
            for (Node node : nodeRow)
                node.reset();

        begin.g = 0.0f;
        begin.h = distance(begin, end);
        searching.add(begin);

        while (searching.size() > 0) {
            Node current = searching.get(0);

            //Finding node with the smallest f-cost
            for (Node node : searching) {
                if (node.f < current.f || node.f == current.f && node.h < current.h) {
                    current = node;
                }
            }

            processed.add(current);
            searching.remove(current);

            for (Node neighbor : current.neighbors) {
                if (neighbor.obstacle || processed.contains(neighbor)) continue;
                boolean notBeingSearched = !searching.contains(neighbor);
                float costToNeighbor = current.g + distance(current, neighbor) - neighbor.row;

                if (notBeingSearched || costToNeighbor < neighbor.g) {
                    neighbor.g = costToNeighbor;
                    neighbor.prev = current;
                    if (notBeingSearched) {
                        neighbor.h = distance(neighbor, end);
                        neighbor.f = neighbor.g + neighbor.h;
                        searching.add(neighbor);
                    }
                }
            }
        }
        makePath();
    }

    //Connects the nodes from the beginning to end
    void makePath() {
        Node node = end;
        path.clear();
        while (node != null) {
            path.add(0, node);
            node = node.prev;
        }
    }

    //Get distance between two nodes
    int distance(Node nodeA, Node nodeB) {
        //Change in row and change in column
        int dRow = abs(nodeA.row - nodeB.row);
        int dCol = abs(nodeA.col - nodeB.col);
        //Pythagorean
        double distance = sqrt(dRow*dRow + dCol*dCol);
        //Easier to look at
        return (int) floor(distance * 10);
    }

    public ArrayList<Node> path() {
        return path;
    }

    Color color = new Color(37, 169, 156, 60);
    public void draw(Graphics2D g, int x, int y) {
        if (path == null) return;
        for (Node node : path) {
            drawRectangle(node.col * 48 + x, node.row * 48 + y, 48, 48, true, color, g);
        }
    }
}
