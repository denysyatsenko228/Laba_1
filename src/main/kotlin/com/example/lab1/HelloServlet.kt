package com.example.lab1

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File

@WebServlet(name = "helloServlet", value = ["/hello"])
class HelloServlet : HttpServlet() {
    private lateinit var message: String

    override fun init() {
        message = "Hello World!"
    }

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        // Запись информации в журнал сервера
        servletContext.log("HTTP method: ${request.method}")
        servletContext.log("IP address: ${request.remoteAddr}")
        servletContext.log("User-Agent: ${request.getHeader("User-Agent")}")
        servletContext.log("Request parameters: ${request.parameterMap}")

        val path = servletContext.getRealPath("") + File.pathSeparator + "file.txt"

        val file = File(path)

        file.appendText("name\t${request.getParameter("name")}\n")

        response.writer.use { out ->
            out.println("<html><body><ul>")
            file.forEachLine { line ->
                out.println("<li>$line</li>")
            }
            out.println("</ul></body></html>")
        }
    }

    override fun destroy() {
    }
}
