package com.cassbana.antifraud.mapper

/**
 * Mapping interface for mapping from the [From] format to the [To] format
 *
 * @param <From> Input Format
 * @param <To> Output Format
 */
interface RSIMapper<From, To> {
    fun map(inputFormat: From): To
}