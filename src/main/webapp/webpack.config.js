let config={
    entry: ["babel-polyfill", './main.js'],


    output: {
        path: __dirname + "/configuration",
        filename: 'bundle.js'
    },

    devServer:{
        inline:true,
        port:9876

    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015', 'react'],
                    plugins: ["transform-class-properties"]
                }
            }
        ]
    }

};

module.exports = config
