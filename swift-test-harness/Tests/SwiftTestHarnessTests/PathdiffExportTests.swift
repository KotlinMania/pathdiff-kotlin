import Testing
import Pathdiff

@Suite("Pathdiff Swift Export Suite")
struct PathdiffExportTests {
    @Test("Swift module loads cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true), "Pathdiff swift module imported cleanly")
    }

    @Test("diffPaths computes relative paths correctly")
    func diffPathsWorks() {
        let result = diffPaths(path: "/foo/bar/baz", base: "/foo/bar")
        #expect(result == "baz")
    }
}
