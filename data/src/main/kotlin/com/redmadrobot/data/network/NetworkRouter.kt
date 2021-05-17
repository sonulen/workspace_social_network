package com.redmadrobot.data.network

object NetworkRouter {
    const val BASE_HOSTNAME = "interns2021.redmadrobot.com"

    //region Auth
    private const val auth = "auth"
    const val AUTH_REGISTRATION = "$auth/registration"
    const val AUTH_LOGIN = "$auth/login"
    const val AUTH_LOGOUT = "$auth/logout"
    const val AUTH_REFRESH = "$auth/refresh"
    //endregion

    //region Feed
    private const val feed = "feed"
    const val FEED_LIKE = "$feed/{id}/like"
    //endregion

    //region Me
    private const val me = "me"
    const val ME_GET_PROFILE = me
    const val ME_CHANGE_PROFILE = me
    const val ME_GET_FRIEND_LIST = "$me/friends"
    const val ME_ADD_FRIEND = "$me/friends"
    const val ME_DELETE_FRIEND = "$me/friends/{id}"
    const val ME_GET_POSTS = "$me/posts"
    const val ME_ADD_POST = "$me/posts"
    //endregion

    //region Search
    private const val search = "me"
    const val SEARCH = search
    //endregion
}
