package network

import api.network.UserInfo

internal class UserInfoImpl(
    private val _name: String,
    private val _surname: String,
    private val _age: Int
) : UserInfo {
    override fun getSurname() = _surname

    override fun getName() = _name

    override fun getAge() = _age
}