// port-lint: source src/lib.rs
package io.github.kotlinmania.pathdiff

import kotlin.test.Test
import kotlin.test.assertEquals

class LibTest {

    @Test
    fun testAbsolute() {
        // Absolute paths look different on Windows vs Unix.
        fun abs(path: String): String = "/$path"

        assertDiffPaths(abs("foo"), abs("bar"), "../foo")
        assertDiffPaths(abs("foo"), "bar", abs("foo"))
        assertDiffPaths("foo", abs("bar"), null)
        assertDiffPaths("foo", "bar", "../foo")
    }

    @Test
    fun testIdentity() {
        assertDiffPaths(".", ".", "")
        assertDiffPaths("../foo", "../foo", "")
        assertDiffPaths("./foo", "./foo", "")
        assertDiffPaths("/foo", "/foo", "")
        assertDiffPaths("foo", "foo", "")

        assertDiffPaths("../foo/bar/baz", "../foo/bar/baz", "")
        assertDiffPaths("foo/bar/baz", "foo/bar/baz", "")
    }

    @Test
    fun testSubset() {
        assertDiffPaths("foo", "fo", "../foo")
        assertDiffPaths("fo", "foo", "../fo")
    }

    @Test
    fun testEmpty() {
        assertDiffPaths("", "", "")
        assertDiffPaths("foo", "", "foo")
        assertDiffPaths("", "foo", "..")
    }

    @Test
    fun testRelative() {
        assertDiffPaths("../foo", "../bar", "../foo")
        assertDiffPaths("../foo", "../foo/bar/baz", "../..")
        assertDiffPaths("../foo/bar/baz", "../foo", "bar/baz")

        assertDiffPaths("foo/bar/baz", "foo", "bar/baz")
        assertDiffPaths("foo/bar/baz", "foo/bar", "baz")
        assertDiffPaths("foo/bar/baz", "foo/bar/baz", "")
        assertDiffPaths("foo/bar/baz", "foo/bar/baz/", "")

        assertDiffPaths("foo/bar/baz/", "foo", "bar/baz")
        assertDiffPaths("foo/bar/baz/", "foo/bar", "baz")
        assertDiffPaths("foo/bar/baz/", "foo/bar/baz", "")
        assertDiffPaths("foo/bar/baz/", "foo/bar/baz/", "")

        assertDiffPaths("foo/bar/baz", "foo/", "bar/baz")
        assertDiffPaths("foo/bar/baz", "foo/bar/", "baz")
        assertDiffPaths("foo/bar/baz", "foo/bar/baz", "")
    }

    @Test
    fun testCurrentDirectory() {
        assertDiffPaths(".", "foo", "../.")
        assertDiffPaths("foo", ".", "foo")
        assertDiffPaths("/foo", "/.", "foo")
    }

    private fun assertDiffPaths(path: String, base: String, expected: String?) {
        assertEquals(expected, diffPaths(path, base))
        assertEquals(expected, diffUtf8Paths(path, base))
    }
}
