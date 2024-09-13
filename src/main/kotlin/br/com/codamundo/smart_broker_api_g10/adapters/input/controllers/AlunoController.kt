package br.com.codamundo.smart_broker_api_g10.adapters.input.controllers

import br.com.codamundo.smart_broker_api_g10.application.ports.input.AlunoInput
import br.com.codamundo.smart_broker_api_g10.shared.dto.AlunoRequestBodyDto
import br.com.codamundo.smart_broker_api_g10.shared.dto.controllers.responses.AlunoResponse
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated


@RestController
@RequestMapping("alunos")
class AlunoController(private val alunoInput: AlunoInput) {

    @GetMapping("/{id}")
    fun getAluno(
        @PathVariable id: Long,
        @RequestHeader("Authorization") authorization: String,
        @RequestHeader(value = "X-Request-ID", required = false) requestId: String?
    ): ResponseEntity<AlunoResponse> {
        val aluno = alunoInput.getAluno(id)
        // Aqui você poderia logar o requestId para rastrear a requisição
        return ResponseEntity.ok(aluno)
    }

    @PostMapping
    fun createAluno(
        @RequestHeader("Authorization") authorization: String,
        @RequestHeader(value = "X-Request-ID", required = false) requestId: String?,
        @RequestBody @Validated alunoRequestBodyDto: AlunoRequestBodyDto
    ): ResponseEntity<AlunoResponse> {
        val novoAluno = alunoInput.createAluno(alunoRequestBodyDto)
        return ResponseEntity.status(201).body(novoAluno)
    }

    @PatchMapping("/{id}")
    fun updateAluno(
        @PathVariable id: Long,
        @RequestHeader("Authorization") authorization: String,
        @RequestHeader(value = "X-Request-ID", required = false) requestId: String?,
        @RequestBody @Validated alunoRequest: AlunoRequestBodyDto
    ): ResponseEntity<Void> {
        alunoInput.updateAluno(id, alunoRequest)
        return ResponseEntity.noContent().build()
    }
}
