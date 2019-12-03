package pl.kamilszustak.hulapp.exception

class NoInternetConnectionException : Exception {

    constructor() : super("No Internet connection available")

    constructor(message: String) : super(message)
}