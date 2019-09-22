call runcrud
if "%ERRORLEVEL%" == "0" goto startbrowser
echo.
echo runcrud has errors - breaking work
goto fail

:startbrowser
start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end

:fail
echo.
echo Some error occured

:end
echo.
echo Work is done