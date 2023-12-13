const express = require('express');
const app = express();
const port = process.env.PORT || 3000 ;

app.get('/', (req, res) => {
    const htmlResponse = `
        <html>
            <body>
                <h1>Hola</h1>
            </body>
        </html>
    `;
    res.send(htmlResponse);
});

app.listen(port, () => {
    console.log(`Server running on port ${port}`);
})