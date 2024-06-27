def calculate_beauty(a, config, n):
    beauty = 0
    i = 0
    while i < n:
        if config & (1 << i):
            start = i
            while i < n and (config & (1 << i)):
                i += 1
            end = i - 1
            beauty += a[start][end - start]
        else:
            i += 1
    return beauty


def find_top_k_beauties(t, test_cases):
    results = []
    for case in test_cases:
        n, k, a = case

        all_beauties = []

        # Iterate over all possible configurations (2^n)
        for config in range(1 << n):
            beauty = calculate_beauty(a, config, n)
            all_beauties.append(beauty)

        # Sort and get the top k beauties
        all_beauties.sort(reverse=True)
        top_k_beauties = all_beauties[:k]

        results.append(top_k_beauties)

    return results


# Example usage:
t = 3
test_cases = [
    (5, 10, [[3, 8, 4, 8, 0], [-8, -9, -8, 9], [1, -8, -8], [1, 7], [8]]),
    (4, 10, [[2, -8, -9, -4], [-1, 0, -8], [0, -7], [8]]),
    (5, 10, [[5, -8, 1, 2, 6], [-6, 5, 7, 1], [7, 2, 0], [-1, 3], [-1]])
]

results = find_top_k_beauties(t, test_cases)
for res in results:
    print(' '.join(map(str, res)))
