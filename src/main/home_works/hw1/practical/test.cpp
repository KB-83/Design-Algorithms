#include <iostream>
#include <iomanip> // For std::flush

int main() {
    std::cout << "Hello, this is a test message!" << std::endl;
    std::cout << std::flush; // Flush output buffer
    return 0;
}
