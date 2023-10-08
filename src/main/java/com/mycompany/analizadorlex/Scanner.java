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
                case 4:
                    if(c == '='){
                        Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.LESS, lexema);
                        tokens.add(t);
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;
                case 7:
                    if(c == '='){
                        Token t = new Token(TipoToken.EQUAL_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.EQUAL, lexema);
                        tokens.add(t);
                        i--;
                    }

                    estado = 0;
                    lexema = "";
                    break;
                case 10:
                    if(c == '='){
                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.BANG, lexema);
                        tokens.add(t);
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;
            //----- PARTE PARA LOS IDENTIFICADORES Y PALABRAS RESERVADAS
                case 13:
                if(Character.isLetterOrDigit(c)){
                    estado = 13;
                    lexema += c;
                }
                else{
                    TipoToken tt = palabrasReservadas.get(lexema);

                    if(tt == null){
                        Token t = new Token(TipoToken.IDENTIFIER, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(tt, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                }
                break;
      //PARTE DE RECONOCIMIENTO DE TOKENS
                case 15:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }else if(c == '.'){
                        estado = 16;
                        lexema += c;
                    }else if(c == 'E'){
                        estado = 18;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);
                        //Va a estado de acpetacion
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 16:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }else if(c == 'E'){
                        estado = 18;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);
                        //Va a estado de acpetacion
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if (c == '+' || c == '-') {
                        lexema += c;
                        estado = 19;
                    }else if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 19:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 20:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }else{
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(tt == null){
                            Token t = new Token(TipoToken.NUMBER, lexema);
                            tokens.add(t);
                        }else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }
                        //Va a estado de acpetacion
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                    //Parte de reconocimiento de cadena
                    case 24:
                    if(Character.isJavaIdentifierPart(c)){
                        estado = 24;
                        lexema += c;
                    }else if(c == '"'){
                        Token t = new Token(TipoToken.STRING, lexema, String.valueOf(lexema));
                        tokens.add(t);
                        //vas a estado de aceptación
                        estado = 0;
                        lexema = "";
                    }else if(c == '\n'){
                        //ERROR EN CADENA CUANDO TENGAMOS: "SALTO DE LINEA" 
                        System.out.println("Error");
                    }
                    else{
                        lexema += c;
                    }
                    break;
                   
                case 26:
                    if(c == '*'){
                        estado = 27;
                        lexema += c;
                    }else if(c == '/'){
                        estado = 30;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.SLASH, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 27:
                    if(c == '*'){
                        estado = 28;
                        lexema += c;
                    }else{
                        estado = 27;
                        lexema += c;
                    }
                    break;
                case 28:
                    if(c == '*'){
                        estado = 28;
                        lexema += c;
                    }else if(c == '/'){
                        estado = 0;
                        lexema = "";
                    }else{
                        estado = 27;
                        lexema += c;
                    }
                    break;
                case 30:
                    if(c == '\n'){
                        estado = 0;
                        lexema = "";
                    }else{
                        estado = 30;
                        lexema += c;
                    }

                    break;

            }
        }

        return tokens;
    }
}