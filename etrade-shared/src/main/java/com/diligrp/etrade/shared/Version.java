package com.diligrp.etrade.shared;

/**
 * 系统版本
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public enum Version {

    // 当前版本
    CURRENT(1, 0, 0);

    private static final int VERSION_LEN = 3;

    private int major;
    private int minor;
    private int maintain;

    Version(int major, int minor, int maintain) {
        this.major = major;
        this.minor = minor;
        this.maintain = maintain;
    }

    public boolean compatibleWith(Version version) {
        return compareWith(version) >= 0;
    }

    public int compareWith(Version version) {
        if (version == null) {
            return 1;
        }

        int result = major - version.major;
        if (result == 0) {
            result = minor - version.minor;
            if (result == 0) {
                result = maintain - version.maintain;
            }
        }

        return result;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getMaintain() {
        return maintain;
    }

    public void setMaintain(int maintain) {
        this.maintain = maintain;
    }

    public String toVersion() {
        return toString();
    }

    @Override
    public String toString() {
        return major + Constants.CHAR_DOT + minor + Constants.CHAR_DOT + maintain;
    }

    public static Version fromVersion(String version) {
        try {
            String[] versions = version.split("\\" + Constants.CHAR_DOT);
            if (versions.length < VERSION_LEN) {
                throw new IllegalArgumentException("Invalid version string format: " + version + ", but 1.0.1 expected");
            }
            Version[] values = values();
            for (Version value : values) {
                if (Integer.parseInt(versions[0]) == value.major && Integer.parseInt(versions[1]) == value.minor
                        && Integer.parseInt(versions[2]) == value.maintain) {
                    return value;
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid version string format: " + version + ", but 1.0.1 expected", ex);
        }
        throw new IllegalArgumentException("Version: " + version + " not exists");
    }

    public static Version currentVersion() {
        return Version.CURRENT;
    }
}
