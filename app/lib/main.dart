import 'package:claims_app/pages/form_page.dart';
import 'package:flutter/material.dart';

void main() => runApp(const ClaimsApp());

class ClaimsApp extends StatelessWidget {
  const ClaimsApp({super.key});

  @override
  Widget build(BuildContext context) {
    const appTitle = 'Claims App';

    return MaterialApp(
      title: appTitle,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: const Text(appTitle),
        ),
        body: const FormPage(),
      ),
    );
  }
}