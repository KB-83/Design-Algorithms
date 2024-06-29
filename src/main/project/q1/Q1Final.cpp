#include <iostream>
#include <vector>
#include <queue>
#include <sstream>

using namespace std;

class MyHeap : public priority_queue<int, vector<int>, greater<int> > {
public:
    int k;

    MyHeap(int initialCapacity) {
        k = initialCapacity;
    }

    void addToHeap(int value) {
        if (this->size() < k) {
            this->push(value);
        } else if (value > this->top()) {
            this->pop();
            this->push(value);
        }
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);

    int t;
    cin >> t;

    while (t--) {
        int n, k;
        cin >> n >> k;

        vector<vector<int> > beauty(n + 1, vector<int>(n + 1, 0));
        for (int i = 1; i <= n; ++i) {
            for (int j = i; j <= n; ++j) {
                cin >> beauty[i][j];
            }
        }

        vector<vector<int> > whiteDP(n + 1, vector<int>(k, 0));
        vector<int> blackDP(k, 0);
        blackDP[0] = beauty[1][1];
        whiteDP[1][0] = 0;

        vector<int> pre(n + 1, 1);
        bool reach = false;
        for (int i = 1; i <= n; ++i) {
            if (!reach) {
                pre[i] = min(k, pre[i - 1] * 2);
                if (pre[i] == k) {
                    reach = true;
                }
            } else {
                pre[i] = k;
            }
        }

        for (int j = 2; j <= n; ++j) {
            MyHeap wData(k);
            MyHeap bData(k);
            int till = pre[j - 2];
            for (int i = 0; i < till; ++i) {
                wData.addToHeap(whiteDP[j - 1][i]);
            }
            for (int i = 0; i < till; ++i) {
                wData.addToHeap(blackDP[i]);
            }

            bData.addToHeap(beauty[1][j]);
            for (int s = j - 1; s >= 1; --s) {
                int min = bData.top();
                till = pre[s - 1];
                for (int z = 0; z < till; ++z) {
                    if (whiteDP[s][z] + beauty[s + 1][j] > min || bData.size() < k) {
                        bData.addToHeap(whiteDP[s][z] + beauty[s + 1][j]);
                        min = bData.top();
                    } else {
                        break;
                    }
                }
            }

            till = min(k, (int)wData.size()) - 1;
            for (int q = till; q >= 0; --q) {
                whiteDP[j][q] = wData.top();
                wData.pop();
            }
            till = min(k, (int)bData.size()) - 1;
            for (int q = till; q >= 0; --q) {
                blackDP[q] = bData.top();
                bData.pop();
            }
        }

        int bTill = min(pre[n - 1], (int)blackDP.size());
        int wTill = min(pre[n - 1], (int)whiteDP[n].size());
        int w = 0;
        int b = 0;
        for (int num = 0; num < k; ++num) {
            if (w < wTill && b < bTill) {
                if (whiteDP[n][w] >= blackDP[b]) {
                    cout << whiteDP[n][w] << " ";
                    ++w;
                } else {
                    cout << blackDP[b] << " ";
                    ++b;
                }
            } else if (w < wTill) {
                cout << whiteDP[n][w] << " ";
                ++w;
            } else if (b < bTill) {
                cout << blackDP[b] << " ";
                ++b;
            }
        }
        cout << "\n";
    }

    return 0;
}
