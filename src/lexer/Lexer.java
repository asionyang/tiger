package lexer;

import static control.Control.ConLexer.dump;

import java.io.PushbackInputStream;

import lexer.Token.Kind;
import util.Todo;

public class Lexer
{
  String fname; // the input file name to be compiled
  PushbackInputStream fstream; // input stream for the above file
  int ln = 1 ;

  public Lexer(String fname, PushbackInputStream fstream)
  {
    this.fname = fname;
    this.fstream = fstream;
  }

  // When called, return the next token (refer to the code "Token.java")
  // from the input stream.
  // Return TOKEN_EOF when reaching the end of the input stream.
  private Token nextTokenInternal() throws Exception
  {
    int c = this.fstream.read();
    if (-1 == c)
      // The value for "lineNum" is now "null",
      // you should modify this to an appropriate
      // line number for the "EOF" token.
      return new Token(Kind.TOKEN_EOF, ln);

    // skip all kinds of "blanks"
    while (' ' == c || '\t' == c || '\n' == c) {
      c = this.fstream.read();
      if ('\n' == c){
        ln++;
      }
    }
    if (-1 == c)
      return new Token(Kind.TOKEN_EOF, ln);

    switch (c) {
      case '+':
        return new Token(Kind.TOKEN_ADD, ln);
      case '&':
        c = this.fstream.read();
        if (c == '&'){
          return new Token(Kind.TOKEN_AND, ln);
        }
      case '=':
        return new Token(Kind.TOKEN_ASSIGN, ln);
      case ',':
        return new Token(Kind.TOKEN_COMMER, ln);
      case '.':
        return new Token(Kind.TOKEN_DOT, ln);
      case '{':
        return new Token(Kind.TOKEN_LBRACE, ln);
      case '[':
        return new Token(Kind.TOKEN_LBRACK, ln);
      case '(':
        return new Token(Kind.TOKEN_LPAREN, ln);
      case '<':
        return new Token(Kind.TOKEN_LT, ln);
      case '!':
        return new Token(Kind.TOKEN_NOT, ln);
      case '}':
        return new Token(Kind.TOKEN_RBRACE, ln);
      case ']':
        return new Token(Kind.TOKEN_RBRACK, ln);
      case ')':
        return new Token(Kind.TOKEN_RPAREN, ln);
      case ';':
        return new Token(Kind.TOKEN_SEMI, ln);
      case '-':
        return new Token(Kind.TOKEN_SUB, ln);
      case '*':
        return new Token(Kind.TOKEN_TIMES, ln);
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        do{c = this.fstream.read();}
        while(Character.isDigit(c) );
        this.fstream.unread(c);
        return new Token(Kind.TOKEN_NUM, ln);
      
      default:
        if(Character.isLetter(c)|| c == '_' || c == '$'){
          String tt = String.valueOf(c);
          c = this.fstream.read();
          while(Character.isLetter(c) || Character.isDigit(c) || c =='_' || c == '$'){
            tt = tt.concat(String.valueOf(c));
            c = this.fstream.read();
          }
          this.fstream.unread(c);
          switch (tt){
            case "boolean":
              return new Token(Kind.TOKEN_BOOLEAN, ln);
            case "class":
              return new Token(Kind.TOKEN_CLASS, ln);
            case "else":
              return new Token(Kind.TOKEN_ELSE, ln);
            case "extends":
              return new Token(Kind.TOKEN_EXTENDS, ln);
            case "false":
              return new Token(Kind.TOKEN_FALSE, ln);
            case "if":
              return new Token(Kind.TOKEN_IF, ln);
            case "int":
              return new Token(Kind.TOKEN_INT, ln);
            case "length":
              return new Token(Kind.TOKEN_LENGTH, ln);
            case "main":
              return new Token(Kind.TOKEN_MAIN, ln);
            case "new":
              return new Token(Kind.TOKEN_NEW, ln);
            case "out":
              return new Token(Kind.TOKEN_OUT, ln);
            case "println":
              return new Token(Kind.TOKEN_PRINTLN, ln);
            case "public":
              return new Token(Kind.TOKEN_PUBLIC, ln);
            case "return":
              return new Token(Kind.TOKEN_RETURN, ln);
            case "static":
              return new Token(Kind.TOKEN_STATIC, ln);
            case "String":
              return new Token(Kind.TOKEN_STRING, ln);
            case "System":
              return new Token(Kind.TOKEN_SYSTEM, ln);
            case "this":
              return new Token(Kind.TOKEN_THIS, ln);
            case "true":
              return new Token(Kind.TOKEN_TRUE, ln);
            case "void":
              return new Token(Kind.TOKEN_VOID, ln);
            case "while":
              return new Token(Kind.TOKEN_WHILE, ln);
            default:
              return new Token(Kind.TOKEN_ID, ln);
          }
        }
      // Lab 1, exercise 2: supply missing code to
      // lex other kinds of tokens.
      // Hint: think carefully about the basic
      // data structure and algorithms. The code
      // is not that much and may be less than 50 lines. If you
      // find you are writing a lot of code, you
      // are on the wrong way.
      new Todo();
      return null;
    }
  }

  public Token nextToken()
  {
    Token t = null;

    try {
      t = this.nextTokenInternal();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    if (dump)
      System.out.println(t.toString());
    return t;
  }
}
