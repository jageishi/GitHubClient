package org.ageage.githubclient.common.exception

sealed class ApiException(cause: Throwable) : Exception(cause)

class NetworkException(cause: Throwable) : ApiException(cause)

class ForbiddenException(cause: Throwable) : ApiException(cause)

class ServerErrorException(cause: Throwable) : ApiException(cause)

class UnexpectedException(cause: Throwable) : ApiException(cause)

