[#ftl]
[#assign s=JspTaglibs["http://stripes.sourceforge.net/stripes.tld"]]
<html>
  <head>
      <title>My First Stripe</title>
      <style type="text/css">
          input.error { background-color: yellow; }
      </style>
  </head>
  <body>
    <h1>Stripes Calculator - FTL</h1>

    Hi, I'm the Stripes Calculator. I can only do addition. Maybe, some day, a nice programmer
    will come along and teach me how to do other things?

    [@s.form action="/examples/quickstart/Calculator.action"]
        [@s.errors/]
        <table>
            <tr>
                <td>Number 1:</td>
                <td>[@s.text name="numberOne"/]</td>
            </tr>
            <tr>
                <td>Number 2:</td>
                <td>[@s.text name="numberTwo"/]</td>
            </tr>
            <tr>
                <td colspan="2">
                    [@s.submit name="addition" value="Add"/]
                    [@s.submit name="division" value="Divide"/]
                </td>
            </tr>
            <tr>
                <td>Result:</td>
                <td>${(actionBean.result)!}</td>
            </tr>
        </table>
    [/@]
  </body>
</html>