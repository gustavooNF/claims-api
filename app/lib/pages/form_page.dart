
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../components/custom_form_field.dart';
import '../components/image_form_field.dart';

class FormPage extends StatefulWidget {
  const FormPage({Key? key}) : super(key: key);
  @override
  _FormPageState createState() => _FormPageState();
}
class _FormPageState extends State<FormPage> {
  final _formKey = GlobalKey<FormState>();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              CustomFormField(
                hintText: 'Name',
                inputFormatters: [
                  FilteringTextInputFormatter.allow(
                    RegExp(r"[a-zA-Z]+|\s"),
                  )
                ],
                validator: (val) {
                  if (val != "" || val == null) return 'Enter valid name';
                },
              ),
              CustomFormField(
                hintText: 'Email',
                validator: (val) {
                  if (val != "" || val == null) return 'Enter valid email';
                },
              ),
              CustomFormField(
                hintText: 'Contact Reason',
                validator: (val) {
                  if (val != "" || val == null) return 'Enter contact reason';
                },
              ),
              CustomFormField(
                hintText: 'Description',
                validator: (val) {
                  if (val != "" || val == null) return 'Enter description';
                },
              ),
              ImageFormField(
                validator: (val) {
                  if (val == null) return 'Pick a picture';
                },
                onChanged: (file) {},
              ),
              ElevatedButton(
                onPressed: () {
                  _formKey.currentState!.validate();
                },
                child: const Text('Submit'),
              )
            ],
          ),
        ),
      ),
    );
  }
}