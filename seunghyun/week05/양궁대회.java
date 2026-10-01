/**
 * @풀이
 *
 * DFS + 백트래킹
 *
 * 라이언이 0점부터 10점까지 각 점수에 몇 발의 화살을 쏠지
 * 모든 경우의 수를 탐색하면서 가장 큰 점수 차이를 만드는 경우를 찾음
 *
 * 각 점수마다
 * - 라이언이 어피치보다 더 많이 맞히는 경우
 * - 라이언이 해당 점수에 화살을 사용하지 않는 경우
 *
 * 를 선택할 수 있음
 *
 * DFS가 끝났을 때 라이언의 점수와 어피치의 점수를 계산하여
 * 현재까지의 최대 점수 차이와 비교
 *
 * 같은 점수 차이라면 낮은 점수를 더 많이 맞힌 경우를 선택해야 하므로
 * 10점부터 0점까지 비교하면서 우선순위를 판단
 *
 *
 * @처리순서
 *
 * 1. 10점부터 0점까지 각 점수를 순서대로 탐색
 *
 * 2. 현재 점수에서 라이언이 어피치보다 1발 더 많이 쏘는 경우 선택
 *    -> 해당 점수를 가져가기 위해 필요한 화살 수만큼 사용
 *
 * 3. 해당 점수에 화살을 사용하지 않는 경우도 탐색
 *
 * 4. DFS가 끝까지 도달하면
 *    -> 남은 화살을 0점에 모두 사용
 *    -> 라이언과 어피치의 점수를 계산
 *
 * 5. 현재 점수 차이가 기존 최대 점수 차이보다 크다면
 *    현재 결과를 정답으로 갱신
 *
 * 6. 점수 차이가 같은 경우
 *    낮은 점수를 더 많이 맞힌 결과를 선택
 *    -> 0점부터 비교하지 않고 10점부터 역순으로 비교하면
 *       낮은 점수를 많이 맞힌 결과를 우선 선택할 수 있음
 *
 * 7. 모든 경우의 수를 탐색한 후 정답 반환
 *
 *
 * @시간복잡도
 *
 * 각 점수마다 선택 / 미선택의 2가지 경우가 있으므로 O(2^N)
 *
 *
 * @공간복잡도
 *
 * O(N)
 *
 */

class Solution {

    // 최대 점수 차이
    int maxDiff = 0;

    // 최종 정답
    int[] answer = {-1};

    public int[] solution(int n, int[] info) {

        // 라이언의 점수별 화살 개수
        int[] lion = new int[11];

        // DFS 시작
        dfs(0, n, info, lion);

        return answer;
    }

    // DFS
    public void dfs(int score, int arrows, int[] info, int[] lion) {

        // 10점부터 0점까지 모든 점수를 확인한 경우
        if (score == 11) {
            // 남은 화살을 0점에 모두 사용
            lion[10] += arrows;

            // 라이언과 어피치의 점수 계산
            int lionScore = 0;
            int apeachScore = 0;

            for (int i = 0; i < 11; i++) {
                // 둘 다 해당 점수를 맞히지 못했다면
                // 점수를 얻을 수 없음
                if (info[i] == 0 && lion[i] == 0) {
                    continue;
                }

                // 라이언이 더 많이 맞힌 경우
                if (lion[i] > info[i]) {
                    lionScore += 10 - i;
                }

                // 어피치가 더 많이 맞힌 경우
                else {
                    apeachScore += 10 - i;
                }
            }

            // 점수 차이
            int diff = lionScore - apeachScore;

            // 라이언이 이긴 경우에만 정답 후보
            if (diff > 0) {
                // 기존보다 점수 차이가 큰 경우
                if (diff > maxDiff) {
                    maxDiff = diff;
                    answer = lion.clone();
                }

                // 점수 차이가 같은 경우
                // 낮은 점수를 더 많이 맞힌 결과를 선택
                else if (diff == maxDiff && isBetter(lion)) {
                    answer = lion.clone();
                }
            }

            // 0점에 사용했던 화살을 다시 되돌림
            lion[10] -= arrows;

            return;
        }

        // 현재 점수
        int point = 10 - score;

        // 현재 점수를 얻기 위해 필요한 화살 수
        int need = info[score] + 1;

        /*
         * 1. 현재 점수에 화살을 사용하는 경우
         *
         * 어피치보다 1발 더 쏘면 해당 점수를 가져갈 수 있음
         */
        if (arrows >= need) {
            lion[score] = need;

            dfs(
                score + 1,
                arrows - need,
                info,
                lion
            );

            // 백트래킹
            lion[score] = 0;
        }

        /*
         * 2. 현재 점수에 화살을 사용하지 않는 경우
         *
         * 라이언이 해당 점수를 포기하고
         * 다음 점수를 탐색
         */
        dfs(
            score + 1,
            arrows,
            info,
            lion
        );
    }

    /*
     * 점수 차이가 같은 경우
     *
     * 낮은 점수를 더 많이 맞힌 결과가 우선이다.
     *
     * 따라서 0점부터 비교해서
     * 더 많은 화살을 가진 결과를 선택한다.
     */
    public boolean isBetter(int[] lion) {

        for (int i = 10; i >= 0; i--) {
            if (lion[i] > answer[i]) {
                return true;
            }

            if (lion[i] < answer[i]) {
                return false;
            }
        }

        return false;
    }
}
