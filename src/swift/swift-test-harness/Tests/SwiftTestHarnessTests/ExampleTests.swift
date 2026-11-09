#if canImport(Testing)
import Testing
import Pathdiff

@Suite("Pathdiff Swift Export Tests")
struct PathdiffExampleTests {
    @Test("Pathdiff swift module imported cleanly")
    func testSwiftModuleLoads() throws {
        #expect(diffPaths(path: "/foo/bar/baz", base: "/foo/bar") == "baz")
    }
}
#elseif canImport(XCTest)
import XCTest
import Pathdiff

final class PathdiffExampleTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertEqual(diffPaths(path: "/foo/bar/baz", base: "/foo/bar"), "baz")
    }
}
#endif
