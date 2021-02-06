import collections

# Define the BFS Algorithm functionality

def bfs(start_x, start_y, end_x, end_y, n, m, matrix, visited, parent_map):
    queue = collections.deque([(start_x, start_y)])

    while queue:
        x, y = queue.popleft()
        visited[x][y] = True

        if x == end_x and y == end_y:
            return

        # Exploration of possibilities (either left, right, top or bottom).
        for next_x, next_y in ((x + 1, y), (x, y + 1), (x - 1, y + 1), (x, y - 1)):
            if (0 <= next_x < n and 0 <= next_y < m
                and matrix[next_x][next_y] != 'X' and not visited[next_x][next_y]):
                
                queue.append((next_x, next_y)) # Adding elements at the end of data.
                parent_map[next_x + next_y * m] = x + y * m #

# Define the Path Gathering (get_path)

def path_gather(start_x, start_y, end_x, end_y, n, m, parent_map, path):
    start = start_x + start_y * m
    parent = parent_map.get(end_x + end_y * m, None)

    print(parent, start)

    while parent and start != parent:
        path.append((parent % m, parent // n))
        parent = parent_map.get(parent, None)
        
    return path

# Define the Optimized short_path

def short_path(matrix):
    if not matrix or not matrix[0]:
        return []
    
    n, m = len(matrix), len(matrix[0])
    start_x, start_y = -1, -1
    end_x, end_y = -1, -1

    for i in range(n):
        for j in range(m):
            if matrix[i][j] == 'S':
                start_x, start_y = i, j
            elif matrix[i][j] == 'E':
                end_x, end_y = i, j

    path = []
    
    if start_x != -1 and end_x != -1:
        visited = [[False] * m for _ in range(n)]
        parent_map = {}
        bfs(start_x, start_y, end_x, end_y, n, m, matrix, visited, parent_map)

        path = path_gather(start_x, start_y, end_x, end_y, n, m, parent_map, [(end_x, end_y)])
        path.append((start_x, start_y))
        
    return path[::-1]

# If the name == to the main then matrix (map representation)
# the short path of the matrix (map) will be shown.

if __name__ == '__main__':
    matrix = [['1', '1', '1', 'X', '1'],
              ['1', 'X', 'S', 'X', 'E'],
              ['1', 'X', 'X', '1', '1'],
              ['1', '1', '1', '1', 'X'],
              ['1', '1', 'X', 'X', 'X']]

    print(short_path(matrix))