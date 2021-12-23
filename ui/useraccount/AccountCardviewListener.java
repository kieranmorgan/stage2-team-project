package com.example.teamproject.ui.useraccount;

/**
 * @author Evan Hosking
 * Used to make accounts cardview clickable
 */

public interface AccountCardviewListener<T> {
    void onItemClick(T data);
}
