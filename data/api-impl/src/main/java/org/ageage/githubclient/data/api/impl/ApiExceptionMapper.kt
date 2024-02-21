package org.ageage.githubclient.data.api.impl

import org.ageage.githubclient.exception.ForbiddenException
import org.ageage.githubclient.exception.NetworkException
import org.ageage.githubclient.exception.ServerErrorException
import org.ageage.githubclient.exception.UnexpectedException
import retrofit2.HttpException
import java.io.IOException

interface ApiExceptionMapper {

    fun map(exception: Exception): Exception
}


internal class ApiExceptionMapperImpl : ApiExceptionMapper {

    override fun map(exception: Exception): Exception {
        return when (exception) {
            is IOException -> {
                NetworkException(exception)
            }

            is HttpException -> {
                when (exception.code()) {
                    403 -> {
                        ForbiddenException(exception)
                    }

                    else -> {
                        ServerErrorException(exception)
                    }
                }
            }

            else -> {
                UnexpectedException(exception)
            }
        }
    }
}