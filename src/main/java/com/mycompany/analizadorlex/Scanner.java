/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;
    //Declaramos las palabras claves para el analizador lexico
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    public Scanner(String source){
        this.source = source + " ";
    }
    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        char c;
        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }else if(c == '>'){
                        estado = 1;
                        lexema += c;
                    }else if(c == '<'){
                        estado = 4;
                        lexema += c;
                    }else if(c == '='){
                        estado = 7;
                        lexema += c;
                    }else if(c == '!'){
                        estado = 10;
                        lexema += c;
                    }else if(c == '"'){
                        estado = 24;
                    }else if(c == '/'){
                        estado = 26;
                        lexema += c;
                    }else if(c == '+'){
                        Token t = new Token(TipoToken.PLUS,lexema);
                        tokens.add(t);
                    }else if(c == '-'){
                        Token t = new Token(TipoToken.MINUS,lexema);
                        tokens.add(t);
                    }else if(c == '.'){
                        Token t = new Token(TipoToken.DOT,lexema);
                        tokens.add(t);
                    }else if(c == ','){
                        Token t = new Token(TipoToken.COMMA,lexema);
                        tokens.add(t);
                    }else if(c == ')'){
                        Token t = new Token(TipoToken.RIGHT_PAREN,lexema);
                        tokens.add(t);
                    }else if(c == '('){
                        Token t = new Token(TipoToken.LEFT_PAREN,lexema);
                        tokens.add(t);
                    }else if(c == '}'){
                        Token t = new Token(TipoToken.RIGHT_BRACE,lexema);
                        tokens.add(t);
                    }else if(c == '{'){
                        Token t = new Token(TipoToken.LEFT_BRACE,lexema);
                        tokens.add(t);
                    }else if(c == '*'){
                        Token t = new Token(TipoToken.STAR,lexema);
                        tokens.add(t);
                    }else if(c == ';'){
                        Token t = new Token(TipoToken.SEMICOLON,lexema);
                        tokens.add(t);
                    }
                    break;
                case 1:
                    if(c == '='){
                        Token t = new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.GREATER, lexema);
                        tokens.add(t);
                        i--;
                    }

                    estado = 0;
                    lexema = "";
                    break;

}
