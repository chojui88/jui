/**
 * @풀이
 *
 * DFS + 백트래킹
 *
 * 모음 ['A', 'E', 'I', 'O', 'U']를 이용하여
 * 길이가 1 ~ 5인 모든 단어를 사전 순으로 만들어보면서
 * 주어진 단어가 몇 번째인지 찾음
 *
 * 단어를 하나 만들 때마다 순서를 1씩 증가시키고,
 * 현재 만들어진 단어가 target과 같다면
 * 해당 순서를 정답으로 반환
 *
 * 단어의 길이가 5가 되면 더 이상 문자를 추가하지 않음
 *
 *
 * @처리순서
 *
 * 1. 현재 단어를 하나 만든다.
 *
 * 2. 단어가 만들어질 때마다 순서를 1 증가시킨다.
 *
 * 3. 현재 단어가 찾는 단어와 같다면
 *    현재 순서를 정답으로 저장한다.
 *
 * 4. 아직 길이가 5보다 작다면
 *    A, E, I, O, U를 하나씩 추가하면서 DFS 실행
 *
 * 5. DFS가 끝나면 마지막에 추가한 문자를 제거
 *    -> 백트래킹
 *
 * 6. 모든 경우를 사전 순으로 탐색하므로
 *    target을 만났을 때의 순서가 곧 사전에서의 위치
 *
 *
 * @시간복잡도
 *
 * O(N^2)
 *
 *
 * @공간복잡도
 *
 * O(N)
 *
 */

class Solution {

    // 사용할 수 있는 모음
    char[] vowels = {'A', 'E', 'I', 'O', 'U'};

    // 현재까지 몇 번째 단어인지
    int count = 0;

    // 정답
    int answer = 0;

    public int solution(String word) {
        // DFS 시작
        dfs("", word);

        return answer;
    }

    // DFS
    public void dfs(String current, String target) {
        // 현재 단어가 target과 같다면
        if (current.equals(target)) {
            answer = count;
            return;
        }

        // 길이가 5라면 더 이상 문자를 추가할 수 없음
        if (current.length() == 5) {
            return;
        }

        // A, E, I, O, U 순서대로 탐색
        for (int i = 0; i < 5; i++) {

            // 현재 단어 뒤에 모음 하나 추가
            String next = current + vowels[i];

            // 새로운 단어 하나를 만들었으므로 순서 증가
            count++;

            // 다음 단어 탐색
            dfs(next, target);

            // 이미 정답을 찾았다면 더 이상 탐색할 필요 없음
            if (answer != 0) {
                return;
            }
        }
    }
}
