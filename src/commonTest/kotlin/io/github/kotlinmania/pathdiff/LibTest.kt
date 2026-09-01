// port-lint: tests lib.rs
package io.github.kotlinmania.pathdiff

import kotlin.test.Test
import kotlin.test.assertEquals

internal fun abs(path: String): String {
    return "/$path"
}

internal fun assertDiffPaths(
    path: String,
    base: String,
    expected: String?,
) {
    assertEquals(expected, diffPaths(path, base))
    assertEquals(expected, diffUtf8Paths(path, base))
}

class LibTest {
    @Test
    fun testAbsolute() {
        assertDiffPaths(abs("foo"), abs("bar"), "../foo")
        assertDiffPaths(abs("foo"), "bar", abs("foo"))
        assertDiffPaths("foo", abs("bar"), null)
        assertDiffPaths("foo", "bar", "../foo")
    }

    @Test
    fun testWindowsDriveAbsolute() {
        fun winAbs(path: String): String = "C:\\$path"
        assertDiffPaths(winAbs("foo"), winAbs("bar"), "../foo")
        assertDiffPaths(winAbs("foo"), "bar", winAbs("foo"))
        assertDiffPaths("foo", winAbs("bar"), null)
    }

    @Test
    fun testDocumentationExamples() {
        assertDiffPaths("/foo/bar", "/foo/bar/baz", "..")
        assertDiffPaths("/foo/bar/baz", "/foo/bar", "baz")
        assertDiffPaths("/foo/bar/quux", "/foo/bar/baz", "../quux")
        assertDiffPaths("/foo/bar/baz", "/foo/bar/quux", "../baz")
        assertDiffPaths("/foo/bar", "/foo/bar/quux", "..")

        assertDiffPaths("/foo/bar", "baz", "/foo/bar")
        assertDiffPaths("/foo/bar", "/baz", "../foo/bar")
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
}
