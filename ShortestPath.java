import java.util.*;
//Cell represents a location on the grid
//We represent each location as Cell so that we can have a hashmap built to store the parent child map
class Cell {
     int x;
     int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}


class Solution4 {
    int[] xDirs = new int[] {0,0,1, -1};
    int[] yDirs = new int[] {1,-1, 0, 0};

    void bfs(char[][] grid, Cell start, List<int[]> path) {
        Queue<Cell> bfsQueue = new LinkedList<>();
        bfsQueue.add(start);
        HashMap<Cell, Cell> parentMap = new HashMap<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Cell endCell = null;
        while(!bfsQueue.isEmpty()) {
            boolean flag = false;
            Cell from = bfsQueue.poll();

            for (int k = 0; k < xDirs.length; ++k) {
                int nextX = from.x + xDirs[k];
                int nextY = from.y + yDirs[k];

                if (nextX < 0 || nextX >= grid.length || nextY < 0 || nextY >= grid[0].length || grid[nextX][nextY] == 'X' || visited[nextX][nextY]) {
                    continue;
                }

                visited[nextX][nextY] = true;
                Cell nextCell = new Cell(nextX, nextY);
                bfsQueue.add(nextCell);
                //we need a way to determine from where we have reached here
                //storing the child to parent mapping, this will be used to retrieve the entire path
                parentMap.put(nextCell, from);
                if (grid[nextX][nextY] == 'E') {
                    endCell = new Cell(nextX, nextY);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        Stack<Cell> stack = new Stack<>();
        stack.push(endCell);

        //build the path from destination to source
        while (true) {
            Cell fromCell = parentMap.get(endCell);
            stack.push(fromCell);
            if (fromCell == start) break;
            endCell = fromCell;
        }
       //reverse the above path and conver as List<int[]>
        while (!stack.isEmpty()) {
            Cell p = stack.pop();
            path.add(new int[] {p.x, p.y});
        }
    }

    List<int[]> shortestPath(char[][] grid) {
        ArrayList<int[]> path = new ArrayList<>();
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] == 'S') {
                    bfs(grid, new Cell(i, j), path);
                }
            }
        }
        return path;
    }
}

public class ShortestPath {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        char[][] grid = {{'1', '1', 'X', '1', '1'},
                        {'S', '1', 'X', '1', '1'},
                        {'1', '1', '1', '1', '1'},
                        {'X', '1', '1', 'E', '1'},
                        {'1', '1', '1', '1', 'X'}};

        List<int[]> path = solution.shortestPath(grid);
        System.out.println("Path length:" + (path.size() - 1));
        path.stream().forEach(i -> {
            System.out.println("{" + i[0] + "," + i[1] + "}");
        });

    }
}